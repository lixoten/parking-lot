// Stage 5/5: Carspotting
// What did I use: Data class override toString, finally used filters, lambda, function objects

package parking

import java.lang.IndexOutOfBoundsException

const val LOT_NOT_CREATED_MSG = "Sorry, a parking lot has not been created."

data class Car(val regNo: String? = null, val color: String? = null) {
    override fun toString(): String {
        return "$regNo $color"
    }
}

class ParkingSpot(var spotNumber: Int = 0, var car: Car? = null){

    fun assign(_car: Car): String {
        car = _car

        return "${_car.color} car parked in spot $spotNumber."
    }

    fun free(spot: Int): String {
        car = null

        return "Spot $spot is free."
    }

    fun display() {
        println("$spotNumber " + this.car.toString())
    }

    fun isEeempty(): Boolean {
        if (this.car == null){
            return true
        }
        return false
    }
}

class ParkingLot(n: Int = 0) {
    val spotts = Array(n) { ParkingSpot() }

    var command = emptyList<String>()
    var cmd = ""
    var regNo = ""
    var carColor = ""
    var spotNo = 0
    var lotSize = 0
    //var myFavColor = ""
    //lateinit var parkingLot: MutableList<Boolean>
    //private lateinit var spots: MutableList<String>

    var parkingLotSpots = MutableList(0) { ParkingSpot() }

    init {
        // Load some test data
        if (false) {
            lotSize = 30

            var i = 0
            parkingLotSpots = MutableList(lotSize) { ParkingSpot(++i) }
            parkingLotSpots[0].assign(Car("KA-01-HH-1111", "White"))
            parkingLotSpots[1].assign(Car("KA-01-HH-2222", "blue"))
            parkingLotSpots[2].assign(Car("KA-01-HH-3333", "Red"))
            parkingLotSpots[3].assign(Car("KA-01-HH-4444", "Red"))
            parkingLotSpots[4].assign(Car("KA-01-HH-5555", "green"))
            parkingLotSpots[5].assign(Car("KA-01-HH-6666", "Red"))
            parkingLotSpots[8].assign(Car("KA-01-HH-9999", "blue"))
        }
    }

    fun displayFullSpots() {
        if (lotSize == 0) {
            println(LOT_NOT_CREATED_MSG)
            return
        }

        val fullSpots = parkingLotSpots.filter { s:ParkingSpot -> s.car != null }

        if (fullSpots.isEmpty()){
            println("Parking lot is empty.")
        } else {
            for (spot in fullSpots){
                spot.display()
            }
        }
    }

    fun getSpot2(code: Char, parm: String): String {
        var resultSet = emptyList<ParkingSpot>()
        if (code == 'c'){
            resultSet = parkingLotSpots.filter { s:ParkingSpot -> s.car?.color?.lowercase() == parm.lowercase() }
        }
        if (code == 'r'){
            resultSet = parkingLotSpots.filter { s:ParkingSpot -> s.car?.regNo == parm }
        }

        if (resultSet.isEmpty()){
            if (code == 'c') {
                return "No cars with color $parm were found."
            } else {
                return "No cars with registration number $parm were found."
            }
        }

        var hold = ""
        for (z in resultSet) {
            hold += "${z.spotNumber}, "
        }

        return hold.trim(',',' ')
    }

    fun getRegColor(color: String): String {
        val resultSet = parkingLotSpots.filter { s:ParkingSpot -> s.car?.color?.lowercase() ==  color.lowercase() }

        if (resultSet.isEmpty()){
            return "No cars with color $color were found."
        }
        var hold = ""
        for (z in resultSet) {
            hold += "${z.car?.regNo}, "
        }

        return hold.trim(',',' ')
    }


    fun run() {
        while (true) {
            command = readln().split(" ").toMutableList()

            //::myFavColor.set("BLUEEEE")
            //if (::spots.isInitialized){
            //    println("DDDDDD")
            //}
            parceCommand(command)
            when (cmd) {
                "reg_by_color" -> println( xxx(carColor,::getRegColor))
                "spot_by_color" -> println( xxx2('c', carColor,::getSpot2))
                "spot_by_reg" -> println( xxx2('r', regNo,::getSpot2))
                "create" -> println(createLot(lotSize))
                "park" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else processPark(regNo, carColor))
                "leave" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else processLeave(spotNo))
                "status" -> displayFullSpots()
                "exit" -> break
                else -> println("Invalid...try again")
            }
        }
    }

    fun xxx (carColor: String, myFunc: (String) -> String): String {
        return if (lotSize == 0)
            LOT_NOT_CREATED_MSG
        else
            myFunc(carColor)
        //myFun
    }

    fun xxx2 (code: Char, parm: String, myFunc: (Char, String) -> String): String {
        return if (lotSize == 0)
            LOT_NOT_CREATED_MSG
        else
            myFunc(code, parm)
    }

    fun parceCommand(input: List<String>) {
        cmd = input[0]
        when(cmd) {
            "park" -> {
                regNo = input[1]
                carColor = input[2]
            }
            "leave" ->  {
                spotNo = input[1].toInt()
            }
            "reg_by_color" ->  {
                carColor = input[1]
            }
            "spot_by_color" ->  {
                carColor = input[1]
            }
            "spot_by_reg" ->  {
                regNo = input[1]
            }
            "create" -> {
                lotSize = input[1].toInt()
            }
            "show" -> {

            }
            "status" -> {
            }
        }
    }

    fun processPark(regNo: String, carColor: String): String {
        spotNo = findOpenSpace()

        if (spotNo < 0) {
            return "Sorry, the parking lot is full."
        } else {
            return parkingLotSpots[spotNo - 1].assign(Car(regNo, carColor))
        }
    }

    fun findOpenSpace(): Int {
        for (spot in parkingLotSpots) {
            if (spot.car === null)
                return spot.spotNumber
        }
        return -1
    }

    fun createLot(lotSize: Int): String {
        var i = 0
        parkingLotSpots = MutableList(lotSize) { ParkingSpot(++i) }

        return "Created a parking lot with $lotSize spots."
    }

    fun processLeave(spotNo: Int): String {
        try {
            if (parkingLotSpots[spotNo - 1].isEeempty()) {
                return "There is no car in spot $spotNo."
            }
        } catch (e: IndexOutOfBoundsException){
            return "Invalid spot number $spotNo, Out of bounds."
        }

        return parkingLotSpots[spotNo - 1].free(spotNo)
    }
}

fun main() {
    val lotObj = ParkingLot()
    lotObj.run()
}// 240 241