package com.music.transfer.telegram.telegram;

import com.music.transfer.telegram.oidc.OidcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@Slf4j
public class TelegramBot extends AbilityBot {

    private final OidcService oidcService;

    public TelegramBot(TelegramClient telegramClient,
                       OidcService oidcService) {
        super(telegramClient, "musicTransferBot");
        onRegister();
        this.oidcService = oidcService;
    }

    @Override
    public long creatorId() {
        return 1L;
    }

    public Ability onUnauthorized() {
        return Ability.builder()
                .name(DEFAULT)
                .privacy(Privacy.PUBLIC)
                .locality(Locality.ALL)
                .action(ctx -> {
                    final var userId = ctx.user().getId();
                    if (oidcService.isAuthenticated(userId)) {
                        silent.sendMd(showMenu(), ctx.chatId());
                    } else {
                        final var message = String.format("Please, [log in](%s).",
                                oidcService.start(userId));
                        silent.sendMd(message, ctx.chatId());
                    }
                })
                .build();
    }

    private String showMenu() {
        return "Test";
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        if (!update.hasMessage()) {
//            log.debug("Update has no message. Skip processing.");
//            return;
//        }
//
//        // Id Telegram-пользователя.
//        var userId = update.getMessage().getFrom().getId();
//        var chatId = update.getMessage().getChatId();
//        // Запрашиваем UserInfo (структуру с информацией о пользователе,
//        // полученной от сервера авторизации) по id Telegram-пользователя.
//        oidcService.findUserInfo(userId).ifPresentOrElse(
//                userInfo -> greet(userInfo, chatId),
//                () -> askForLogin(userId, chatId));
//    }
//
//    private void greet(UserInfo userInfo, Long chatId) {
//        // Здесь могло быть обращение к смежному сервису с использованием
//        // токена доступа. При этом с точки зрения смежного сервиса обращение
//        // бы выполнялось от имени пользователя, приславшего боту сообщение.
//        var username = userInfo.getPreferredUsername();
//        var message = String.format(
//                "Hello, <b>%s</b>!\nYou are the best! Have a nice day!",
//                username);
//        sendHtmlMessage(message, chatId);
//    }
//
//    private void askForLogin(Integer userId, Long chatId) {
//        // Формируем URL для аутентификации пользователя
//        // (см. шаг 2 на схеме взаимодействия).
//        var url = oidcService.getAuthUrl(userId);
//        var message = String.format("Please, <a href=\"%s\">log in</a>.", url);
//        sendHtmlMessage(message, chatId);
//    }


}
