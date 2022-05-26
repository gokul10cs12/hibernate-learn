package com.example.hibernatelearn.Controller;


import com.example.hibernatelearn.Model.Child;
import com.example.hibernatelearn.Model.Parent;
import com.example.hibernatelearn.Repository.ChildRepository;
import com.example.hibernatelearn.Repository.ParentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/")
public class ChildParentController {

    private final ChildRepository childRepository;

    private final ParentRepository parentRepository;

    public ChildParentController(ChildRepository childRepository, ParentRepository parentRepository) {
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
    }


    @PostMapping(path = "/child")
    Child createChildRecord(@RequestBody Child child){
        return childRepository.save(child);
    }

    @DeleteMapping(path = "/child")
    void removeChildRecord(@RequestBody Child child){
        childRepository.delete(child);
    }

    @DeleteMapping(path = "/parent")
    void removeParentRecord(@RequestBody Parent parent){
        parentRepository.delete(parent);
    }


    @PostMapping(path = "/parent")
    Parent createParentRecord(@RequestBody Parent parent){
        Parent updated = parentRepository.save(parent);
        return updated;
    }

    @GetMapping(path = "/parent")
    List<Parent> getAllParents(){
        return parentRepository.findAll();
    }

    @GetMapping(path = "/child")
    List<Child> getAllChildren(){
        List<Child> children = childRepository.findAll();
        return children;
    }

    @GetMapping(path = "/parent/{id}")
    Optional<Parent> getSingleParent(@PathVariable("id") Long id){
        return parentRepository.findById(id);
    }
    @GetMapping(path = "/child/{id}")
    Optional<Child> getSingleChild(@PathVariable("id") Long id){
        return childRepository.findById(id);
    }
}
