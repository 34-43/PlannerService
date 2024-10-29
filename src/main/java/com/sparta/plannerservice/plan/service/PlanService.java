package com.sparta.plannerservice.plan.service;

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
        // 계획의 User 콜렉션 필드를 현재 사용자로 갱신합니다.
//        plan.getUsers().add(jwtUser);
        // 해당 계획을 생성하여 영속화 합니다.
        Plan retrievedPlan = planRepository.save(plan);
        // 현재 사용자의 계획 필드도 전용 영속성 전이 메서드로 갱신합니다.
        updateUserWithPlan(jwtUser, retrievedPlan);

        return retrievedPlan;
    }

    @Transactional
    public List<Plan> readPlans(User jwtUser, int page, int size) {
        // repository 를 사용하지 않고, 현재 사용자의 plans 필드 셋을 리스트로 반환합니다.
        // Todo - page 와 size 값 활용
        return jwtUser.getPlans().stream().toList();
    }

    public Plan readPlan(User jwtUser, UUID id) {
        // id 에 해당하는 계획을 일단 가져옵니다.
        Plan retrievedPlan = planRepository.findByIdSafe(id);
        // 계획이 사용자와 연결되어 있는 지 점검합니다.
        checkUserPlanMatched(jwtUser, retrievedPlan);

        return retrievedPlan;
    }

    @Transactional
    public void updatePlan(User jwtUser, UUID id, Plan plan) {
        // id 에 해당하는 계획을 일단 가져옵니다.
        Plan retrievedPlan = planRepository.findByIdSafe(id);
        // 계획이 사용자와 연결되어 있는 지 점검합니다.
        checkUserPlanMatched(jwtUser, retrievedPlan);
        // 계획 갱신 (유저 외래 키는 갱신 필요 x)
        retrievedPlan.setTitle(plan.getTitle());
        retrievedPlan.setContent(plan.getContent());
    }

    @Transactional
    public void joinUserInPlan(User jwtUser, UUID id, UUID userId) {
        // 사용자를 추가하고자 하는 계획을 가져옵니다.
        Plan retrievedPlan = planRepository.findByIdSafe(id);
        // 추가하고자 하는 사용자를 가져옵니다.
        User retrievedUser = userRepository.findByIdSafe(userId);
        // 계획이 '현재' 사용자와 연결되어 있는 지 점검합니다.
        checkUserPlanMatched(jwtUser, retrievedPlan);
        // 이후, 계획에 추가하고자 하는 사용자를 추가합니다.
        retrievedPlan.getUsers().add(retrievedUser);
    }

    @Transactional
    public void deletePlan(User jwtUser, UUID id) {
        Plan retrievedPlan = planRepository.findByIdSafe(id);
        checkUserPlanMatched(jwtUser, retrievedPlan);
        planRepository.delete(retrievedPlan);
    }

    @Transactional
    public void updateUserWithPlan(User jwtUser, Plan plan) {
        // 필터에서 가져온 사용자를 save 하여 다시 영속화 합니다.
        jwtUser = userRepository.save(jwtUser);
        // dirty checking 을 통해 사용자 필드를 갱신합니다.
        jwtUser.getPlans().add(plan);
    }

    private void checkUserPlanMatched(User user, Plan plan) {
        if (!user.getPlans().contains(plan)) {
            throw new FailedRequestException("not a member of this plan");
        }
    }

}
