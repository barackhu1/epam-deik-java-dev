package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.dto.BookingDto;
import com.epam.training.ticketservice.core.dto.MovieDto;
import com.epam.training.ticketservice.core.dto.UserDto;
import com.epam.training.ticketservice.core.model.User;
import com.epam.training.ticketservice.core.service.interfaces.BookingServiceInterface;
import com.epam.training.ticketservice.core.service.interfaces.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class AuthenticatorCommand {

    private final UserServiceInterface userServiceInterface;
    private final BookingServiceInterface bookingServiceInterface;

    @ShellMethod(key = "sign out", value = "User logout")
    public String logout() {
        return userServiceInterface.logout()
                .map(userDto -> userDto.userName() + " successfully logged out")
                .orElse("There's no user logged in");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "sign in privileged", value = "User login")
    public String signInPrivileged(String username, String password) {
        return userServiceInterface.signInPrivileged(username, password)
                .map(userDto -> userDto.userName() + " successfully signed in")
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "sign in", value = "User login")
    public String signIn(String username, String password) {
        return userServiceInterface.signIn(username, password)
                .map(userDto -> userDto.userName() + " successfully signed in")
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "sign up", value = "User login")
    public String signUp(String username, String password) {
        try {
            userServiceInterface.signUp(username, password);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "User successfully signed up";
    }

    @ShellMethod(key = "describe account", value = "Get user information")
    public String print() {
        Optional<UserDto> optionalUserDto = userServiceInterface.describeAccount();
        if (optionalUserDto.isPresent()) {
            if (optionalUserDto.get().role().equals(User.Role.ADMIN)) {
                return "Signed in with privileged account '" + optionalUserDto.get().userName() + "'";
            } else {
                String userName = optionalUserDto.get().userName();
                List<BookingDto> signedInUserBookingsDto = bookingServiceInterface.listBookings().stream()
                        .filter(bookingDto -> bookingDto.getUserDto().userName().equals(userName)).toList();

                if (signedInUserBookingsDto.isEmpty()) {
                    return "Signed in with account '" + userName + "'\nYou have not booked any tickets yet";
                }

                String bookingsReturned = signedInUserBookingsDto.stream()
                        .map(BookingDto::toString)
                        .collect(Collectors.joining("\n"));

                return "Signed in with account '" + userName + "'\nYour previous bookings are\n" + bookingsReturned;
            }
        } else {
            return "You are not signed in";
        }
    }

    public Availability isAvailable() {
        if (userServiceInterface.describeAccount().isEmpty()) {
            return Availability.available();
        } else {
            return Availability.unavailable("First sign out");
        }
    }

}