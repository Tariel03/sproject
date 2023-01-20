package com.example.sproject.Services;

import com.example.sproject.Models.News;
import com.example.sproject.Models.User;
import com.example.sproject.Repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<User> findAll(){
        return  clientRepository.findAll();
    }
    public Optional<User> findById(Long id){
        return clientRepository.findById(id);

    }
    public void save(User user){
        clientRepository.save(user);
    }

    public void deletePhoto(User user){
        user.setData(null);
        clientRepository.save(user);
    }


}
