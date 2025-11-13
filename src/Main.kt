data class Destination(
    val name: String,
    val singlePrice: Double,
    val returnPrice: Double,
    var takings: Double = 0.0
) {
    fun addTakings(amount: Double) {
        takings += amount
    }
}

data class Ticket(
    val origin: String,
    val destination: Destination,
    val type: String,  // "Single" or "Return"
    val price: Double
) {
    fun printTicket() {
        println("\n***")
        println(origin)
        println("to")
        println(destination.name)
        println("Price: £${"%.2f".format(price)} [$type]")
        println("***\n")
    }
}

