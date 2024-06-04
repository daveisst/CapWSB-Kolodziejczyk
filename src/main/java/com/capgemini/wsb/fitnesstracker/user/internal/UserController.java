package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Endpoint do pobierania wszystkich użytkowników.
     *
     * @return Lista obiektów UserDto reprezentujących wszystkich użytkowników w systemie.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    /**
     * Endpoint do pobierania użytkownika o określonym identyfikatorze.
     *
     * @param id Identyfikator użytkownika.
     * @return Obiekt UserDto reprezentujący użytkownika o podanym identyfikatorze.
     * @throws RuntimeException Jeśli użytkownik o podanym identyfikatorze nie został znaleziony.
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    /**
     * Endpoint do dodawania nowego użytkownika.
     *
     * @param userDto Obiekt UserDto reprezentujący nowego użytkownika.
     * @return Obiekt UserDto reprezentujący dodanego użytkownika.
     */
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {

        User user = userMapper.toEntity(userDto);
        User savedUser = userService.createUser(user);
        return userMapper.toDto(savedUser);
    }

    /**
     * Endpoint do aktualizowania istniejącego użytkownika.
     *
     * @param id      Identyfikator użytkownika do zaktualizowania.
     * @param userDto Obiekt UserDto zawierający zaktualizowane dane użytkownika.
     * @return Obiekt UserDto reprezentujący zaktualizowanego użytkownika.
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Endpoint do usuwania użytkownika.
     *
     * @param id Identyfikator użytkownika do usunięcia.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Endpoint do wyszukiwania użytkowników po adresie e-mail.
     *
     * @param email Adres e-mail do wyszukania.
     * @return Lista obiektów UserDto reprezentujących użytkowników pasujących do podanego adresu e-mail.
     */
    @GetMapping("/search/email")
    public List<UserDto> searchUsersByEmail(@RequestParam String email) {
        return userService.searchUsersByEmail(email).stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Endpoint do wyszukiwania użytkowników starszych niż określony wiek.
     *
     * @param age Wiek, od którego mają być wyszukiwani użytkownicy.
     * @return Lista obiektów UserDto reprezentujących użytkowników starszych niż podany wiek.
     */
    @GetMapping("/search/age")
    public List<UserDto> searchUsersByAge(@RequestParam int age) {
        return userService.searchUsersByAgeGreaterThan(age).stream()
                .map(userMapper::toDto)
                .toList();
    }

}