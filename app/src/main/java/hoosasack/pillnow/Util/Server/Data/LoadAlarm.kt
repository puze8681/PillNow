package hoosasack.pillnow.Util.Server.Data

import com.google.gson.JsonObject
import org.json.JSONObject
import java.util.*

/**
 * Created by parktaejun on 2017. 8. 20..
 */
data class LoadAlarm(var token: String, var number: String, var alarm : JsonObject)