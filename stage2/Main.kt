// Stage 2/5: Only 2 spots
package parking

fun main() {
    // println("White car has parked.")
    // println("Yellow car left the parking lot.")
    // println("Green car just parked here.")

    val parkingLot = emptyList<String>().toMutableList()
    //parkingLot.add(1,"XXXX")
    var command = emptyList<String>().toMutableList()
    var color = ""
    var regNo = ""

    val carInfo = listOf<String>("black", "11-111-1111")

    command = readln().split(" ").toMutableList()
    if (command[0] == "") {
        error("DDDDDDDDD")
    } else {
//        val arr = parceCommand(command) //<--parms
        if (command[0] == "park") {
            println(processPark(command[1], command[2]))
        } else if (command[0] == "leave") {
            print(processLeave(command[1]))
        } else {
            error("DDDDDDD")
        }
    }
}

fun parceCommand(command: String): List<String> {
    val x = listOf("park", "FFFFF")
    return x
}

fun processPark(regNo: String, color: String): String {
    val slotNo = 2 // findEmptySlot()
//    if (slotNo == 1) {
//        return "Parking lot is full."
//    } else {
        return "$color car parked in spot $slotNo."
//    }
}

fun processLeave(slotNo: String): String {
    if (slotNo == "1") {
        return "Spot $slotNo is free."
    } else {
        return "There is no car in spot $slotNo."
    }
}
