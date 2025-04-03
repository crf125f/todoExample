package com.example.todo.controller;

import com.example.todo.model.TodoForm;
import com.example.todo.model.TodoDto;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.todo.constant.RouteConstants;
import com.example.todo.constant.ViewNames;
import com.example.todo.constant.MessageConstants;

import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    private static final Logger log = LoggerFactory.getLogger(TodoController.class);

    /**
     * 一覧画面の表示
     * - Todoのリスト（DTO）を取得し、Modelに追加
     * - 新規登録用の空のフォーム（TodoForm）もModelに追加
     */
    @GetMapping(RouteConstants.TODOS)
    public String showTodoList(Model model) {
        log.info("GET /todos 一覧画面表示処理開始");

        List<TodoDto> todos = this.todoService.getTodoList();
        model.addAttribute("todos", todos);
        model.addAttribute("todo", new TodoForm());

        log.info("表示件数: {}", todos.size());//新規用の空TodoForm。
        return ViewNames.LIST;
    }

    /**
     * 新規Todoの登録処理
     * - バリデーションチェック
     * - フォーム → DTO に変換してServiceに渡す
     */
    @PostMapping(RouteConstants.TODOS)
    public String addTodo(@ModelAttribute("todo") @Valid TodoForm todoForm,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes){
        log.info("POST /todos 新規登録処理開始: title={}, done={}", todoForm.getTitle(), todoForm.isDone());

        if (bindingResult.hasErrors()){
            log.warn("バリデーションエラー: {}", bindingResult.getAllErrors());
            model.addAttribute("todos", this.todoService.getTodoList());
            return ViewNames.LIST;
        }

        // フォーム → DTO に変換
        TodoDto todoDto = new TodoDto();
        todoDto.setTitle(todoForm.getTitle());
        todoDto.setDone(todoForm.isDone());

        this.todoService.addTodo(todoDto);
        redirectAttributes.addFlashAttribute(MessageConstants.FLASH_MESSAGE, MessageConstants.TODO_ADDED);
        log.info("Todo登録完了");
        return "redirect:" + RouteConstants.TODOS;
    }

    /**
     * エディット画面表示
     * 編集対象のIDを基に、対象のTodoをModelに設定する。
     * @param id
     * @param model
     * @return
     */
    @GetMapping(RouteConstants.TODOS_EDIT)
    public String showEditForm(@PathVariable int id, Model model){
        log.info("GET /todos/edit 編集画面表示処理開始: id={}", id);

        TodoDto dto = this.todoService.findById(id);
        TodoForm form = new TodoForm();
        form.setId(dto.getId());
        form.setTitle(dto.getTitle());
        form.setDone(dto.isDone());

        model.addAttribute("todo", form);

        log.info("編集対象Todo: title={}, done={}", dto.getTitle(), dto.isDone());
        return ViewNames.EDIT;
    }

    /**
     * 入力値でtodoListの対象Todoを更新する
     * @param id    編集対象のTodoのID
     * @param form  編集フォームのTodoオブジェクト
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping(RouteConstants.TODOS_EDIT)
    public String updateTodo(@PathVariable int id,
                             @Valid @ModelAttribute("todo") TodoForm form,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            //@ModelAttribute("todo") を使っている場合、Springが自動的に form をModelに登録してくれるため、通常は明示的な addAttribute は不要です。
            log.warn("Validation error on update:{}", bindingResult.getAllErrors());
            return ViewNames.EDIT;
        }

        TodoDto todoDto = new TodoDto();
        todoDto.setId(form.getId());
        todoDto.setTitle(form.getTitle());
        todoDto.setDone(form.isDone());
        this.todoService.updateTodo(todoDto);
        redirectAttributes.addFlashAttribute(MessageConstants.FLASH_MESSAGE, MessageConstants.TODO_UPDATED);
        return "redirect:" + RouteConstants.TODOS;
    }

    /**
     * 削除処理
     * @param id
     * @return
     */
    @PostMapping(RouteConstants.TODOS_DELETE)
    public String deleteTodo(@PathVariable int id,
                             RedirectAttributes redirectAttributes){
        this.todoService.deleteTodo(id);
        redirectAttributes.addFlashAttribute(MessageConstants.FLASH_MESSAGE, MessageConstants.TODO_DELETED);
        return "redirect:" + RouteConstants.TODOS;
    }

    /**
     * トグル処理
     * @param id
     * @return
     */
    @PostMapping(RouteConstants.TODOS_TOGGLE)
    public String toggleTodo(@PathVariable int id){
        todoService.toggleDone(id);
        return "redirect:" + RouteConstants.TODOS;
    }

}
