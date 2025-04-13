package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.model.Account;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class TaskController {
	@Autowired
	Account account;
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
		List<Task> taskList = null;
		if (categoryId == null || categoryId == 0) {
			taskList = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); // ← IDで昇順
		} else {
			taskList = taskRepository.findByCategoryIdOrderByIdAsc(categoryId); // ← ID順に表示
		}
		model.addAttribute("tasks", taskList);

		return "tasks";
	}

	//新規作成画面の表示
	@GetMapping("/tasks/add")
	public String addTask() {
		return "addtasks";
	}

	//新規作成の実行
	@PostMapping("/tasks/crate")
	public String storeTask(
			@RequestParam(name = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(name = "title", defaultValue = "") String title,
			@RequestParam(name = "closingDate", defaultValue = "") LocalDate closingDate,
			@RequestParam(name = "progress", defaultValue = "") Integer progress,
			@RequestParam(name = "memo", defaultValue = "") String memo,
			Model model) {
		//Taskオブジェクトの生成
		Task task = new Task(categoryId, title, closingDate, progress, memo);
		//tasksテーブルへの反映
		taskRepository.save(task);
		//[tasks]にGETでリクエストしなおす
		return "redirect:/tasks";
	}

	//タスク更新画面表示
	@GetMapping("/tasks/{id}/edit")
	public String editTask(@PathVariable("id") Integer id, Model model) {
		// tasksテーブルをID（主キー）で検索
		Task task = taskRepository.findById(id).get();
		model.addAttribute("task", task);
		return "edittasks";
	}

	//タスク更新の実行
	@PostMapping("/tasks/{id}/update")
	public String update(
			@PathVariable("id") Integer id,
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "closingDate", defaultValue = "") LocalDate closingDate,
			@RequestParam(value = "progress", defaultValue = "") Integer progress,
			@RequestParam(value = "memo", defaultValue = "") String memo,
			Model model) {
		//Taskオブジェクトの生成
		Task task = new Task(id,categoryId, title, closingDate, progress, memo);
		//tasksテーブルへの反映
		taskRepository.save(task);
		//[tasks]にGETでリクエストしなおす
		return "redirect:/tasks";

	}
	// 削除画面表示
	@GetMapping("/tasks/{id}/delete")
	public String confirmDelete(@PathVariable("id") Integer id, Model model) {
	    Task task = taskRepository.findById(id).orElse(null);
	    model.addAttribute("task", task);
	    return "deletetasks";  // → この名前のHTMLテンプレートを表示
	}
	// 削除処理
			@PostMapping("/tasks/{id}/delete")
			public String delete(@PathVariable("id") Integer id, Model model) {

				// itemsテーブルから削除（DELETE）
				taskRepository.deleteById(id);
				// 「/items」にGETでリクエストし直す（リダイレクト）
				return "redirect:/tasks";
			}

}
