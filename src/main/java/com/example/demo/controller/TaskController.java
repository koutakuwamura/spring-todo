package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
@Controller
public class TaskController {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    // タスク一覧表示
    @GetMapping("/tasks")
    public String index(
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            Model model) {
    	// user一覧を取得
        List<User> userList = userRepository.findAll();
        model.addAttribute("users", userList);
        
        // 全カテゴリー一覧を取得
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categories", categoryList);
        

        // タスク一覧情報の取得
        List<Task> taskList= null;
        if (categoryId == null || categoryId == 0) {
            taskList = taskRepository.findAll();
        } else {
            taskList = taskRepository.findByCategoryId(categoryId);
        }
        model.addAttribute("tasks", taskList);
        

        return "tasks";
    }
}
