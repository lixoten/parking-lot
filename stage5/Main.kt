// Stage 5/5: Carspotting
// Change it to play with data class, but not really using it yet
package parking

const val LOT_NOT_CREATED_MSG = "Sorry, a parking lot has not been created."

data class Car(val slot: Int? = null, val regNox: String? = null, val carColorx: String? = null)

//enum class Commands (cmd: String, msg: String, action: (Commands, List<String>) -> List<String>) {
//    //CONFIG("config", "Get and set a username.", {cmd, args -> executeConfig(cmd, args)}),
//    //CREATE("create", "SSSSSSS", {carColorrr -> getRegColor(carColorrr)}),
//    STATUS("status", "SSSSSSSSSS")
//}

class ParkingLot {
    var command = emptyList<String>()
    var cmd = ""
    var regNo = ""
    var carColor = ""
    var slotNo = 0
    var lotSize = 0
    var codeParm = 'c'
    //var myFavColor = ""


    var parkingLot = MutableList(0) { false }
    //lateinit var parkingLot: MutableList<Boolean>
    //private lateinit var spots: MutableList<String>

    var parkingLotCars = MutableList(0) {Car()}

    init {
        // Load some test data
        if (false) {
            lotSize = 4

            parkingLot = MutableList(lotSize) { false }
            parkingLotCars = MutableList(lotSize) { Car() }

            parkingLotCars[0] = Car(1, "KA-01-HH-9999", "White")
            parkingLotCars[1] = Car(2, "KA-01-HH-3672", "White")
            parkingLotCars[2] = Car(3, "Rs-P-N-21", "Red")
            parkingLotCars[3] = Car(4, "Rs-P-N-22", "Red")

            parkingLot[0] = true
            parkingLot[1] = true
            parkingLot[2] = true
            parkingLot[3] = true
            // parkingLot[6] = true
            // parkingLot[9] = true
        }
    }

    fun run() {
        // println("White car has parked.")
        // println("Yellow car left the parking lot.")
        // println("Green car just parked here.")

        while (true) {
            //println("park or leave?")
            command = readln().split(" ").toList()

            //::myFavColor.set("BLUEEEE")
            //if (::spots.isInitialized){
            //    println("DDDDDD")
            //}
            parceCommand(command)
            when (cmd) {
                "reg_by_color" -> println( xxx(carColor,::getRegColor))
                //"reg_by_color" -> println( zzz(code='c', carColor = carColor, ::getRegColor))
                //"reg_by_color" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else getRegColor(carColor))
                //"spot_by_color" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else getSpot('c', carColor))
                "spot_by_color" -> println( xxx2('c', carColor,::getSpot))
                //"spot_by_reg" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else getSpot('r', regNo))
                "spot_by_reg" -> println( xxx2('r', regNo,::getSpot))
                "create" -> println(createLot(command))
                "park" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else processPark(regNo, carColor))
                "leave" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else processLeave(slotNo))
                "status" -> {
                    if (lotSize == 0) println(LOT_NOT_CREATED_MSG)
                    else if (parkingLot.contains(true)) {
                        for (idx in parkingLot.indices) {
                            if (parkingLot[idx] == true){
                                val x = parkingLotCars[idx]
                                println("${idx + 1} ${x.regNox} ${x.carColorx}")
                            }
                        }
                    } else {
                        println("Parking lot is empty.")
                    }
                }
                "exit" -> break
                else -> println("Invalid...try again")
            }
        }
    }


    fun zzz (code: Char='c', carColor: String, myFunc: (Char, String) -> String): String {
        return if (lotSize == 0)
            LOT_NOT_CREATED_MSG
        else
            myFunc(code, carColor)
        //myFun
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
        //myFun
    }


    fun parceCommand(input: List<String>) {
        cmd = input[0]
        when(cmd) {
            "park" -> {
                regNo = input[1]
                carColor = input[2]
            }
            "leave" ->  {
                slotNo = input[1].toInt()
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
        slotNo = findOpenSpace()
        if (slotNo == 0) {
            return "Sorry, the parking lot is full."
        } else {
            parkingLot[slotNo - 1] = true
            val myCar = Car(slotNo, regNo, carColor)
            parkingLotCars[slotNo - 1] = myCar
            return "$carColor car parked in spot $slotNo."
        }
    }

    fun findOpenSpace(): Int {
        for(idx in parkingLot.indices) {
            if (parkingLot[idx] == false){
                return idx + 1
            }
        }
        return 0
    }

    fun getSpot(code: Char, parm: String): String {
        var resultSet = emptyList<Car>()
        if (code == 'c'){
            resultSet = parkingLotCars.filter({x:Car-> x.carColorx?.lowercase() == parm.lowercase()})
        }

        if (code == 'r'){
            resultSet = parkingLotCars.filter({x:Car-> x.regNox?.lowercase() == parm.lowercase()})
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
            hold += "${z.slot}, "
        }

        return hold.trim(',',' ')
    }

    fun getRegColor(color: String): String {
        var resultSet = emptyList<Car>()
        resultSet = parkingLotCars.filter({x:Car-> x.carColorx?.lowercase() == color.lowercase()})

        if (resultSet.isEmpty()){
            return "No cars with color $color were found."
        }
        var hold = ""
        for (z in resultSet) {
            hold += "${z.regNox}, "
        }

        return hold.trim(',',' ')
    }


    fun createLot(params: List<String>): String {
        parkingLot = MutableList(params[1].toInt()) { false }
        parkingLotCars = MutableList(params[1].toInt()) {Car()}

        return "Created a parking lot with $lotSize spots."
        //return "DDDDDD"
    }

    fun processLeave(slotNo: Int): String {
        if (slotNo > lotSize){
            return "Lot size is $lotSize. There is no slot #$slotNo."
        }else if (parkingLot[slotNo - 1] == false){
            return "There is no car in spot $slotNo."
        } else {
            parkingLot[slotNo - 1] = false
            parkingLotCars[slotNo - 1] = Car()

            return "Spot $slotNo is free."
        }
    }
}

fun main() {
    val lotObj = ParkingLot()
    lotObj.run()
} //242
