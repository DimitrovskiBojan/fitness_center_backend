package com.example.fitness_center.service.impl;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MealPlan;
import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.Trainer;
import com.example.fitness_center.model.exceptions.MealPlanNotFoundException;
import com.example.fitness_center.model.exceptions.TrainerNotFoundException;
import com.example.fitness_center.repository.ClientRepository;
import com.example.fitness_center.repository.MealPlanRepository;
import com.example.fitness_center.repository.MyUserRepository;
import com.example.fitness_center.repository.TrainerRepository;
import com.example.fitness_center.service.MealPlanService;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanServiceImpl implements MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final TrainerRepository trainerRepository;

    private final ClientRepository clientRepository;

    private final MyUserRepository userRepository;

    public MealPlanServiceImpl(MealPlanRepository mealPlanRepository, TrainerRepository trainerRepository, ClientRepository clientRepository, MyUserRepository userRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.trainerRepository = trainerRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<MealPlan> findAll() {
        return this.mealPlanRepository.findAll();
    }

    @Override
    public Optional<MealPlan> findById(Long id) {
        return this.mealPlanRepository.findById(id);
    }

    @Override
    public MealPlan save(Long price, String type, Long created_by, String description, String data) {
        MyUser user = this.userRepository.findMyUserById(created_by).orElseThrow(() -> new TrainerNotFoundException(created_by));
        Trainer trainer = this.trainerRepository.findTrainerByUsername(user.getUsername()).orElseThrow(() -> new TrainerNotFoundException(created_by));
        return this.mealPlanRepository.save(new MealPlan(price, type, trainer,description,data));
    }

    @Override
    public MealPlan edit(Long id, Long price, String type, Long created_by, String description, String data) {

        Trainer trainer = this.trainerRepository.findById(created_by).orElseThrow(() -> new TrainerNotFoundException(created_by));
        MealPlan mealPlan = this.mealPlanRepository.findById(id).orElseThrow(() -> new MealPlanNotFoundException(id));

        mealPlan.setPrice(price);
        mealPlan.setType(type);
        mealPlan.setCreated_by(trainer);
        mealPlan.setDescription(description);
        mealPlan.setData(data);

        this.mealPlanRepository.save(mealPlan);

        return mealPlan;
    }

    @Override
    public List<MealPlan> findAllByCreated_by(Long id) {
        Trainer trainer = this.trainerRepository.findById(id).orElseThrow(() -> new TrainerNotFoundException(id));
        return this.mealPlanRepository.findAllByCreated_by(trainer);
    }

    @Override
    public void deleteById(Long id) {
        // Check if the meal plan exists
        MealPlan mealPlan = mealPlanRepository.findById(id)
                .orElseThrow(() -> new MealPlanNotFoundException(id));

        this.mealPlanRepository.deleteById(id);
    }

    @Override
    public MealPlan addPlanToClient(Long planId, String clientUsername) {
        Client client = this.clientRepository.findMyUserByUsername(clientUsername).orElseThrow(() -> new UsernameNotFoundException(clientUsername));
        MealPlan mealPlan = this.mealPlanRepository.findById(planId).orElseThrow(() -> new MealPlanNotFoundException(planId));
        Trainer trainer = mealPlan.getCreated_by();


        client.setCredits((int)(client.getCredits() - mealPlan.getPrice()));
        trainer.setCredits((int)(trainer.getCredits()+mealPlan.getPrice()));
        mealPlan.getPurchasedBy().add(client);
        this.trainerRepository.save(trainer);
        this.mealPlanRepository.save(mealPlan);
        this.clientRepository.save(client);
        return mealPlan;
    }

    @Override
    public boolean canBuy(String planId, String clientUsername) {
        Client client = this.clientRepository.findMyUserByUsername(clientUsername).orElseThrow(() -> new UsernameNotFoundException(clientUsername));
        MealPlan mealPlan = this.mealPlanRepository.findById(Long.parseLong(planId)).orElseThrow(() -> new MealPlanNotFoundException(Long.parseLong(planId)));

        return client.getCredits() >= mealPlan.getPrice();

    }

}
