/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Proyecto.service;

import com.example.Proyecto.model.Cliente;
import com.example.Proyecto.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author FLOMAR
 */
@Service
public class ClienteService {
    
     @Autowired
    private ClienteRepository repository;
    
    /**
     * Funcion para listar la tabla cliente
     * @return 
     */
    public List<Cliente> listarTodas() {
        return repository.findAll();
    }
    
    /**
     * Funcion para guardar datos de una cliente
     * @param cliente 
     */
    public void guardar(Cliente cliente) {
        repository.save(cliente);
    }
    
    /**
     * Funcion para buscar una cliente por id
     * @param id
     * @return 
     */
    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }
    
    /**
     * Funcion para eliminar el registro de una persona
     * @param id 
     */
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}
