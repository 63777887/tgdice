//package com.jwk.tgbot.service;
//
//import com.jwk.tgbot.config.TgProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
//import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.methods.send.SendDice;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@Component
//public class MyBot extends TelegramLongPollingBot {
//
//    @Autowired
//    TgProperties tgProperties;
//        //发送消息
//    private void sendMessage(SendMessage message) {
//        try {
//            super.execute(message); // Call method to send the message
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (update.hasCallbackQuery()) {
//                        CallbackQuery callbackQuery = update.getCallbackQuery();
//            String data = callbackQuery.getData();
//            Long chatId = callbackQuery.getMessage().getChatId();
//                        if (data.equals("throw_dice")) {
//                            SendMessage sendMessage = new SendMessage();
//                            sendMessage.setChatId(chatId.toString());
//                            sendMessage.setText("hhhhh");
//                            sendMessage(sendMessage);
//                        }
//        }
//
//
//        if (update.hasMessage()&&update.getMessage().hasText() && update.getMessage().getText().equals("dice")) {
//
//
//            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//            InlineKeyboardButton rollDiceButton = new InlineKeyboardButton();
//            rollDiceButton.setText("Roll Dice");
//            rollDiceButton.setCallbackData("roll_dice");
//            keyboardButtonsRow1.add(rollDiceButton);
//            keyboardMarkup.setKeyboard(Collections.singletonList(keyboardButtonsRow1));
//
//            SendMessage message = new SendMessage();
//            message.setChatId(String.valueOf(update.getMessage().getChatId()));
//            message.setText("Click the button below to roll the dice!");
//            message.setReplyMarkup(keyboardMarkup);
//
//
//
//
//
//
//            // 如果数据为 "throw_dice"，则发送一个骰子，并等待用户点击它
////            if (data.equals("throw_dice")) {
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//            sendMessage.setText("test");
//            sendMessage(sendMessage);
//                sendDice(update.getMessage().getChatId());
////            } else {
////                 处理其他回调查询
////            }
//        } else if (update.hasMessage()) {
//            Message message = update.getMessage();
//            Long chatId = message.getChatId();
//            if (message.hasText()) {
//                String text = message.getText();
//                // 处理其他消息
//            } else if (message.hasDice()) {
//                // 如果是一个骰子，输出其值
//                Integer diceValue = message.getDice().getValue();
//                System.out.println("The dice value is: " + diceValue);
//            } else {
//                // 处理其他类型的消息
//            }
//        }
//    }
//
//    private void sendDice(Long chatId) {
////        SendDice sendDice = new SendDice();
////        sendDice.setChatId(chatId.toString());
////        sendDice.setEmoji("🎲");
////        sendDice.setReplyMarkup(getDiceKeyboard());
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(chatId.toString());
//        sendMessage.setText("dice");
//        sendMessage.setReplyMarkup(getDiceKeyboard());
//        try {
//            super.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private InlineKeyboardMarkup getDiceKeyboard() {
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
//        List<InlineKeyboardButton> row = new ArrayList<>();
//        InlineKeyboardButton button = new InlineKeyboardButton();
//        button.setText("Roll the Dice!");
//        button.setCallbackData("throw_dice");
//        row.add(button);
//        keyboard.add(row);
//        keyboardMarkup.setKeyboard(keyboard);
//        return keyboardMarkup;
//    }
//
//    @Override
//    public String getBotUsername() {
//        return tgProperties.getBotUsername();
//    }
//
//    @Override
//    public String getBotToken() {
//        return tgProperties.getBotToken();
//    }
//}
