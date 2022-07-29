// Stage 4/5: Size matters
// Change it to play with data class, but not really using it yet
package parking

const val LOT_NOT_CREATED_MSG = "Sorry, a parking lot has not been created."

data class Car(val slot: Int? = null, val regNox: String? = null, val carColorx: String? = null)


class ParkingLot {
    var command = emptyList<String>()
    var cmd = ""
    var regNo = ""
    var carColor = ""
    var slotNo = 0
    var lotSize = 0
    //var myFavColor = ""


    var parkingLot = MutableList(0) { false }
    //lateinit var parkingLot: MutableList<Boolean>
    //private lateinit var spots: MutableList<String>

    var parkingLotCars = MutableList(0) {
        MutableList(0) {Car()}
    }

    fun run() {
        // println("White car has parked.")
        // println("Yellow car left the parking lot.")
        // println("Green car just parked here.")

        while (true) {
            //println("park or leave?")
            command = readln().split(" ").toMutableList()

            //::myFavColor.set("BLUEEEE")
            //if (::spots.isInitialized){
            //    println("DDDDDD")
            //}
            parceCommand(command) //<--parms
            when (cmd) {
                "create" -> println(createLot(lotSize))
                "park" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else processPark(regNo, carColor))
                "leave" -> println(if (lotSize == 0) LOT_NOT_CREATED_MSG else processLeave(slotNo))
                "status" -> {
                    if (lotSize == 0) println(LOT_NOT_CREATED_MSG)
                    else if (parkingLot.contains(true)) {
                        for (idx in parkingLot.indices) {
                            if (parkingLot[idx] == true){
                                val x = parkingLotCars[idx]
                                println("${idx + 1} ${x[0].regNox} ${x[0].carColorx}")
                            }
                        }
                    } else {
                        println("Parking lot is empty.")
                    }
                }
                "exit" -> {
                    break
                    //exitProcess(0)
                }
                else -> println("Invalid...try again")
            }
        }
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
            //parkingLotCars[slotNo - 1][0] = regNo
            //parkingLotCars[slotNo - 1][1] = carColor
            val myCar = Car(slotNo, regNo, carColor)
            parkingLotCars[slotNo - 1][0] = myCar
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

    fun createLot(lotSize: Int): String {
        parkingLot = MutableList(lotSize) { false }
        parkingLotCars = MutableList(lotSize) {
            MutableList(1) {Car(null,null,null)}
        }

        return "Created a parking lot with $lotSize spots."
    }

    fun processLeave(slotNo: Int): String {
        if (slotNo > lotSize){
            return "Lot size is $lotSize. There is no slot #$slotNo."
        }else if (parkingLot[slotNo - 1] == false){
            return "There is no car in spot $slotNo."
        } else {
            parkingLot[slotNo - 1] = false
            parkingLotCars[slotNo - 1][0] = Car()

            return "Spot $slotNo is free."
        }
    }
}

fun main() {
    val lotObj = ParkingLot()
    lotObj.run()
}
