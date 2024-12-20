/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.Proyecto.repository;

import com.example.Proyecto.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author FLOMAR
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
