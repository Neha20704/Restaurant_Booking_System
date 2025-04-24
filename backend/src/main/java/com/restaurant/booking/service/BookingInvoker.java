package com.restaurant.booking.service;

import org.springframework.stereotype.Component;

import com.restaurant.booking.model.Command;

@Component
public class BookingInvoker {

    private Command command;

    // Set command for execution
    public void setCommand(Command command) {
        this.command = command;
    }

    // Execute command
    public String executeCommand() {
        return command.execute();
    }
}
