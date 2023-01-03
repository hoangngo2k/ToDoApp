package com.example.todo.controller;

import com.example.todo.dao.SearchForm;
import com.example.todo.entity.User;
import com.example.todo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ModelAndView getAll(Model model,
                               @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                               @RequestParam(name = "sort", required = false, defaultValue = "asc") String sort,
                               @RequestParam(name = "field", required = false, defaultValue = "id") String field,
                               @ModelAttribute(value = "searchForm") SearchForm searchForm) {
        Sort sortable = null;
        if (sort.equals("asc"))
            sortable = Sort.by(field).ascending();
        if (sort.equals("desc"))
            sortable = Sort.by(field).descending();
        String sortDirection = sort.equals("asc") ? "desc" : "asc";
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        assert sortable != null;
        Pageable pageable = PageRequest.of(page, size, sortable);
        Page<User> userPage = service.getAll(pageable, searchForm.getUsername());
        int totalPages = userPage.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("userList", userPage);
        return new ModelAndView("index");
    }

//    @GetMapping("/search")
//    public ModelAndView getByUsername(@ModelAttribute("searchForm") SearchForm searchForm, Model model) {
//        model.addAttribute("userList", service.getUserByUsername(searchForm.getUsername()));
//        return new ModelAndView("index");
//    }

    @GetMapping("/addnew")
    public ModelAndView addNewEmployee(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return new ModelAndView("newUser");
    }

    @PostMapping("/save")
    public RedirectView saveEmployee(@ModelAttribute("user") User user) {
        service.createUser(user);
        return new RedirectView("/");
    }

    @GetMapping("/showFormForUpdate/{id}")
    public ModelAndView updateForm(@PathVariable(value = "id") long id, Model model) {
        User user = service.getUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("update");
    }

    @PostMapping("/updateUser/{id}")
    public RedirectView updateUser(@PathVariable Long id, @Valid User user, BindingResult result, Model model) {
        if(result.hasErrors()){
            user.setId(id);
            return new RedirectView("/showFormForUpdate/"+id);
        }
        service.editUser(id, user);
        return new RedirectView("/");
    }

    @GetMapping("/deleteUser/{id}")
    public RedirectView deleteTodo(@PathVariable Long id) {
        service.deleteUser(id);
        return new RedirectView("/");
    }
}
