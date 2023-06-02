import com.elbekd.bot.Bot
import com.elbekd.bot.model.toChatId
import com.elbekd.bot.types.Message
import OpenaiAPI

fun main() {
    val token = "6097080206:AAHCzfzmlf7yZUZV2Lom-yLEl8cQxcXUV5o"
    val bot = Bot.createPolling(token)

    bot.onCommand("/start") { (msg, _)->
        try {
            bot.sendMessage(msg.chat.id.toChatId(), "вы нажали на /start")
        } catch(e: Exception) {
            println(e)
        }
    }
    bot.onMessage { msg ->
        try {
            if(msg.photo.isNotEmpty()) {
                bot.sendMessage(msg.chat.id.toChatId(), "вы отправили мне фото")
            }
            else if(msg.voice != null) {
                bot.sendMessage(msg.chat.id.toChatId(), "вы отправили мне голосовое сообщение")
            }
            else if(msg.text != null) {
                val openaiAPI = OpenaiAPI()
                val content = openaiAPI.chatGPT(msg.text as String)
                bot.sendMessage(msg.chat.id.toChatId(), content)
            }
        } catch(e: Exception) {
            println(e)
        }
    }

    bot.start()
}