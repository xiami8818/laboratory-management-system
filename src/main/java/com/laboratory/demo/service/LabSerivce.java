package com.laboratory.demo.service;

import com.laboratory.demo.entry.Lab;
import com.laboratory.demo.mapper.LabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LabSerivce {
    @Autowired
    LabMapper labMapper;

    public List<Lab> getAllLabs() {
        return labMapper.getAllLab();
    }
}
