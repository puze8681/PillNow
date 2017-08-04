package hoosasack.pillnow.Server

/**
 * Created by parktaejun on 2017. 8. 4..
 */
public class ServerRegister {
    var name : String
    var id : String
    var password : String
    var gender : String
    var age : String

    constructor(name: String, id: String, password: String, gender: String, age: String) {
        this.name = name
        this.id = id
        this.password = password
        this.gender = gender
        this.age = age
    }
}