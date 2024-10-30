package com.sparta.plannerservice.plan.service;

import com.sparta.plannerservice.common.enums.FailedRequest;
import com.sparta.plannerservice.common.exception.FailedRequestException;
import com.sparta.plannerservice.plan.entity.Plan;
import com.sparta.plannerservice.plan.repository.PlanRepository;
import com.sparta.plannerservice.user.entity.User;
import com.sparta.plannerservice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final UserRepository userRepository;
    private final PlanRepository planRepository;

    @Transactional
    public Plan createPlan(User jwtUser, Plan plan) {
        // 두 엔티티를 영속화(새로 저장, 찾기)합니다.
        Plan retrievedPlan = planRepository.save(plan);
        jwtUser = userRepository.findByIdSafe(jwtUser.getId());

        // 엔티티 로직으로 관계를 갱신합니다.
        jwtUser.joinPlan(retrievedPlan);

        return retrievedPlan;
    }

    @Transactional
    public List<Plan> readPlans(User jwtUser, int page, int size) {
        // 사용자 측 조인 테이블을 활용합니다.
        // Todo - page 와 size 값 활용
        jwtUser = userRepository.findByIdSafe(jwtUser.getId());
        return jwtUser.getPlans().stream().toList();
    }

    @Transactional
    public Plan readPlan(User jwtUser, UUID id) {
        return getCheckedPersistPlan(jwtUser, id);
    }

    @Transactional
    public void updatePlan(User jwtUser, UUID id, Plan plan) {
        Plan retrievedPlan = getCheckedPersistPlan(jwtUser, id);

        // 계획 엔티티의 내용만 갱신합니다.
        retrievedPlan.setTitle(plan.getTitle());
        retrievedPlan.setContent(plan.getContent());
    }

    @Transactional
    public void deletePlan(User jwtUser, UUID id) {
        Plan retrievedPlan = getCheckedPersistPlan(jwtUser, id);

        for (User u : retrievedPlan.getUsers()) {
            u.getPlans().remove(retrievedPlan);
        }
        retrievedPlan.getUsers().clear();

        planRepository.delete(retrievedPlan);
    }

    @Transactional
    public void joinPlan(User jwtUser, UUID id, UUID userId) {
        Plan retrievedPlan = getCheckedPersistPlan(jwtUser, id);

        // 추가하려는 사용자를 가져와 추가합니다.
        User retrievedUser = userRepository.findByIdSafe(userId);
        retrievedUser.joinPlan(retrievedPlan);
    }

    @Transactional
    public void leavePlan(User jwtUser, UUID id) {
        Plan retrievedPlan = getCheckedPersistPlan(jwtUser, id);
        jwtUser = userRepository.findByIdSafe(jwtUser.getId());
        jwtUser.leavePlan(retrievedPlan);
    }

    /**
     * 사용자 정보와 계획 UUID 의 연관 여부를 확인한 뒤, 영속화된 계획을 반환합니다.
     * @param jwtUser 영속화되지 않은 사용자 엔티티
     * @param planId 계획 id
     * @return 연관 확인이 이뤄진 영속화된 계획 엔티티
     */
    @Transactional
    protected Plan getCheckedPersistPlan(User jwtUser, UUID planId) {
        // 두 엔티티를 모두 영속화 상태로 가져옵니다.
        Plan retrievedPlan = planRepository.findByIdSafe(planId);
        jwtUser = userRepository.findByIdSafe(jwtUser.getId());
        // 연결 상태를 확인합니다.
        if (!retrievedPlan.getUsers().contains(jwtUser)) {
            throw new FailedRequestException(FailedRequest.NOT_YOUR_PLAN);
        }
        // 영속화된 계획 엔티티를 반환합니다.
        return retrievedPlan;
    }

}
