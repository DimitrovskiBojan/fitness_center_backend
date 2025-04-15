package com.example.fitness_center.service.impl;

import com.example.fitness_center.model.Client;
import com.example.fitness_center.model.MyUser;
import com.example.fitness_center.model.Trainer;
import com.example.fitness_center.model.TrainingTerm;
import com.example.fitness_center.model.exceptions.*;
import com.example.fitness_center.repository.ClientRepository;
import com.example.fitness_center.repository.MyUserRepository;
import com.example.fitness_center.repository.TrainerRepository;
import com.example.fitness_center.repository.TrainingTermRepository;
import com.example.fitness_center.service.TrainingTermService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingTermServiceImpl implements TrainingTermService {

    private final TrainingTermRepository trainingTermRepository;
    private final TrainerRepository trainerRepository;

    private final ClientRepository clientRepository;

    private final MyUserRepository myUserRepository;

    public TrainingTermServiceImpl(TrainingTermRepository trainingTermRepository, TrainerRepository trainerRepository, ClientRepository clientRepository, MyUserRepository myUserRepository) {
        this.trainingTermRepository = trainingTermRepository;
        this.trainerRepository = trainerRepository;
        this.clientRepository = clientRepository;
        this.myUserRepository = myUserRepository;
    }

    @Override
    public List<TrainingTerm> findAll() {
        return this.trainingTermRepository.findAll();
    }

    @Override
    public Optional<TrainingTerm> findById(Long id) {

        TrainingTerm trainingTerm = this.trainingTermRepository.findById(id).orElseThrow(() -> new TrainingTermNotFound(id));

        return Optional.of(trainingTerm);
    }

    @Override
    public TrainingTerm save(String startTime, String endTime, String price, String date, Long trainerId) {

        MyUser myUser = this.myUserRepository.findMyUserById(trainerId).orElseThrow(() -> new ClientNotFoundException(trainerId));

        Trainer trainer = this.trainerRepository.findTrainerByUsername(myUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException(myUser.getUsername()));

        TrainingTerm trainingTerm = new TrainingTerm(startTime,endTime,price,date,trainer);
        this.trainingTermRepository.save(trainingTerm);

        return trainingTerm;
    }

    @Override
    public Optional<TrainingTerm> edit(Long id, String startTime, String endTime, String price, String date, Long trainerId) {

        Trainer trainer = this.trainerRepository.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException(trainerId));
        TrainingTerm trainingTerm = this.trainingTermRepository.findById(id).orElseThrow(() -> new TrainingTermNotFound(id));

        trainingTerm.setDate(date);
        trainingTerm.setTrainer(trainer);
        trainingTerm.setPrice(price);
        trainingTerm.setStartTime(startTime);
        trainingTerm.setEndTime(endTime);

        this.trainingTermRepository.save(trainingTerm);

        return Optional.of(trainingTerm);
    }

    @Override
    public void deleteById(Long id) {
        TrainingTerm trainingTerm = this.trainingTermRepository.findById(id).orElseThrow(() -> new TrainingTermNotFound(id));

        this.trainingTermRepository.deleteById(id);

    }

    @Override
    public List<TrainingTerm> findAllForTrainer(Long id) {
        return this.trainingTermRepository.findAllByTrainerIdAndIsReservedFalse(id);
    }

    @Override
    public List<TrainingTerm> findAllForTrainerProfile(Long id) {
        return this.trainingTermRepository.findAllByTrainerId(id);
    }

    @Override
    public void cancelTerm(Long id) {
        TrainingTerm trainingTerm = this.trainingTermRepository.findById(id).orElseThrow(() -> new TrainingTermNotFound(id));
        trainingTerm.setIsReserved(false);
        trainingTerm.setReservedBy(null);
        this.trainingTermRepository.save(trainingTerm);
    }

    @Override
    public boolean canBuy(Long termId, String clientUsername) {
        System.out.println(termId);
        System.out.println(clientUsername);
        Client client = this.clientRepository.findMyUserByUsername(clientUsername).orElseThrow(() -> new UsernameNotFoundException(clientUsername));
        TrainingTerm trainingTerm = this.trainingTermRepository.findById(termId).orElseThrow(() -> new TrainingTermNotFound(termId));

        System.out.println(client.getCredits() + " " + trainingTerm.getPrice() );
        System.out.println(client.getCredits() >= Long.parseLong(trainingTerm.getPrice()));

        return client.getCredits() >= Long.parseLong(trainingTerm.getPrice());

    }

    @Override
    public void addTermToClient(Long termId, String clientUsername) {
        Client client = this.clientRepository.findMyUserByUsername(clientUsername).orElseThrow(() -> new UsernameNotFoundException(clientUsername));
        TrainingTerm trainingTerm = this.trainingTermRepository.findById(termId).orElseThrow(() -> new TrainingTermNotFound(termId));
        client.setCredits(client.getCredits() - Integer.parseInt(trainingTerm.getPrice()));

        Trainer trainer = trainingTerm.getTrainer();
        trainer.setCredits(trainer.getCredits() + Integer.parseInt(trainingTerm.getPrice()));


        this.clientRepository.save(client);
        this.trainerRepository.save(trainer);
        trainingTerm.setReservedBy(client);
        trainingTerm.setIsReserved(true);
        this.trainingTermRepository.save(trainingTerm);
    }

    @Override
    public List<TrainingTerm> findAllForClient(Long id) {


        Client client = this.clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));

        return this.trainingTermRepository.findAllByReservedBy(client);
    }


}
