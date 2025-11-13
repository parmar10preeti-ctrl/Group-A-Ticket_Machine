// COM534 Group Project – Group Member A

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

class TicketMachine(private val originStation: String) {

    // Hard-coded destinations (no database)
    private val destinations = mutableListOf(
        Destination("London", 10.0, 18.0),
        Destination("Portsmouth", 7.5, 13.5),
        Destination("Bournemouth", 6.0, 11.0),
        Destination("Winchester", 4.5, 8.0)
    )

    private var insertedMoney: Double = 0.0

    fun start() {
        println("===================================")
        println(" Welcome to Solent Train Tickets ")
        println("===================================")

        var running = true
        while (running) {
            println("\nSelect an option:")
            println("1. Search for a ticket")
            println("2. Insert money")
            println("3. Buy ticket")
            println("4. Exit")

            when (readlnOrNull()?.trim()) {
                "1" -> searchForTicket()
                "2" -> insertMoney()
                "3" -> buyTicket()
                "4" -> {
                    println("Thank you for using the ticket machine!")
                    running = false
                }
                else -> println("Invalid option, please try again.")
            }
        }
    }

    private fun searchForTicket() {
        println("Available destinations:")
        destinations.forEach { println("- ${it.name}") }

        print("\nEnter destination name: ")
        val destinationName = readlnOrNull()?.trim().orEmpty()

        val dest = destinations.find { it.name.equals(destinationName, ignoreCase = true) }

        if (dest == null) {
            println("Destination not found.")
            return
        }

        print("Ticket type (single/return): ")
        val type = readlnOrNull()?.trim()?.lowercase()

        val price = when (type) {
            "single" -> dest.singlePrice
            "return" -> dest.returnPrice
            else -> {
                println("Invalid ticket type.")
                return
            }
        }

        println("Ticket price: £${"%.2f".format(price)}")
    }

    private fun insertMoney() {
        print("Enter amount to insert: £")
        val amount = readlnOrNull()?.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            println("Invalid amount entered.")
        } else {
            insertedMoney += amount
            println("You inserted £${"%.2f".format(amount)}. Total inserted: £${"%.2f".format(insertedMoney)}")
        }
    }

    private fun buyTicket() {
        print("Enter destination name: ")
        val destinationName = readlnOrNull()?.trim().orEmpty()
        val dest = destinations.find { it.name.equals(destinationName, ignoreCase = true) }

        if (dest == null) {
            println("Destination not found.")
            return
        }

        print("Ticket type (single/return): ")
        val type = readlnOrNull()?.trim()?.lowercase()

        val price = when (type) {
            "single" -> dest.singlePrice
            "return" -> dest.returnPrice
            else -> {
                println("Invalid ticket type.")
                return
            }
        }

        println("Ticket price: £${"%.2f".format(price)}")

        if (insertedMoney >= price) {
            val ticket = Ticket(originStation, dest, type!!.replaceFirstChar { it.uppercase() }, price)
            ticket.printTicket()
            dest.addTakings(price)
            insertedMoney -= price
            println("Remaining balance: £${"%.2f".format(insertedMoney)}")
        } else {
            val shortfall = price - insertedMoney
            println("Not enough money inserted. You need £${"%.2f".format(shortfall)} more.")
        }
    }
}

fun main() {
    val machine = TicketMachine("Southampton Central")
    machine.start()
}
