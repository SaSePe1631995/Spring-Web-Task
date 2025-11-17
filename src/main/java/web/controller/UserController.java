package web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import web.dto.UserCreateDto;
import web.dto.UserUpdateDto;
import web.model.User;
import web.service.UserService;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<UserUpdateDto> userListDto = userService.getAllUsers().stream()
                .peek(System.out::println)
                .map(user -> modelMapper.map(user, UserUpdateDto.class))
                .toList();
        model.addAttribute("users", userListDto);
        return "users";
    }

    @GetMapping("/users/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        UserCreateDto userCreateDto = modelMapper.map(user, UserCreateDto.class);
        model.addAttribute("userCreateDto", userCreateDto);
        model.addAttribute("id", id);
        return "edit-user";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id,
                             @Valid @ModelAttribute("userCreateDto") UserCreateDto userCreateDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("userCreateDto", userCreateDto);
            return "edit-user";
        }
        userService.updateUser(id, userCreateDto);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/users/new")
    public String showCreateForm(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());
        return "create-user";
    }

    @PostMapping("/users/new")
    public String createUser(@Valid @ModelAttribute("userCreateDto") UserCreateDto userCreateDto,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "create-user";
        }
        userService.createUser(userCreateDto);
        return "redirect:/users";
    }
}

