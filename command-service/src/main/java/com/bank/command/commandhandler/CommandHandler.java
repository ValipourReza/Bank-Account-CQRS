package com.bank.command.commandhandler;

import com.bank.domain.command.BaseCommand;

public interface CommandHandler<T extends BaseCommand> {
    void handle(T command);
}