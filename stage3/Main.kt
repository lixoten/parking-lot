// Stage 3/5: Expand and park
// Change it to play with data class, but not really using it yet
package parking

data class Car(val slot: Int?, val regNox: String?, val carColorx: String?)

class ParkingLot {
    // val carInfo = listOf<String>("black", "11-111-1111")
    var command = emptyList<String>()
    var cmd = ""
    var regNo = ""
    var carColor = ""
    var slotNo = 0

    var parkingLot = MutableList(20) { false }
    var parkingLot2 = MutableList(20) {
        Car(null,null,null)
    }

    var parkingLotCars = MutableList(20) {
        MutableList(1) {Car(null,null,null)}
    }

    fun run() {
        // println("White car has parked.")
        // println("Yellow car left the parking lot.")
        // println("Green car just parked here.")

        while (true) {
            //println("park or leave?")
            command = readln().split(" ").toMutableList()
            parceCommand(command) //<--parms
            when (cmd) {
                "park" -> println(processPark(regNo, carColor))
                "leave" -> println(processLeave(slotNo))
                "show" -> {
                    println(parkingLot)
                    println(parkingLotCars)
                }
                "exit" -> break
                else -> println("Invalid...try again")
            }
        }
    }

    fun parceCommand(input: List<String>) {
        cmd = input[0]
        if (input.size == 3) {
            regNo = input[1]
            carColor = input[2]
        }
        if (input.size == 2) {
            slotNo = input[1].toInt()
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

    fun processLeave(slotNo: Int): String {
        if (parkingLot[slotNo - 1] == false){
            return "There is no car in spot $slotNo."
        } else {
            parkingLot[slotNo - 1] = false
            //parkingLotCars[slotNo - 1][0] = ""
            //parkingLotCars[slotNo - 1][1] = ""
            val myCar = Car(null, null, null)
            parkingLotCars[slotNo - 1][0] = myCar

            return "Spot $slotNo is free."
        }
    }
}

fun main() {
    val lotObj = ParkingLot()
    lotObj.run()
}