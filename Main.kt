package machine

const val WATER_FOR_ESPRESSO = 250
const val COFFEE_FOR_ESPRESSO = 16
const val MONEY_FOR_ESPRESSO = 4

const val WATER_FOR_LATTE = 350
const val MILK_FOR_LATTE = 75
const val COFFEE_FOR_LATTE = 20
const val MONEY_FOR_LATTE = 7

const val WATER_FOR_CAPPUCCINO = 200
const val MILK_FOR_CAPPUCCINO = 100
const val COFFEE_FOR_CAPPUCCINO = 12
const val MONEY_FOR_CAPPUCCINO = 6

enum class MachineAction {
    BUY, FILL, TAKE, REMAINING
}

object CoffeeMachine {
    private var water = 400
    private var milk = 540
    private var coffee = 120
    private var disposableCups = 9
    private var money = 550

    var currentAction = MachineAction.REMAINING

    fun printSupply() {
        println()
        println("""
            The coffee machine has:
            $water ml of water
            $milk ml of milk
            $coffee g of coffee beans
            $disposableCups disposable cups
            ${'$'}$money of money
        """.trimIndent())
    }

    fun askAction(): Boolean {
        while (true) {
            println("Write action (buy, fill, take, remaining, exit):")
            when (readln()) {
                "buy" -> {
                    currentAction = MachineAction.BUY
                    return true
                }
                "fill" -> {
                    currentAction = MachineAction.FILL
                    return true
                }
                "take" -> {
                    currentAction = MachineAction.TAKE
                    return true
                }
                "remaining" -> {
                    currentAction = MachineAction.REMAINING
                    return true
                }
                "exit" -> return false
            }
        }
    }

    fun take() {
        println()
        println("I gave you ${'$'}$money")
        money = 0

    }

    fun fill() {
        println()
        println("Write how many ml of water you want to add:")
        water += readln().toInt()
        println("Write how many ml of milk you want to add:")
        milk += readln().toInt()
        println("Write how many grams of coffee beans you want to add:")
        coffee += readln().toInt()
        println("Write how many disposable cups you want to add:")
        disposableCups += readln().toInt()
    }

    private fun makeCoffee(kindOfCoffee: Int) {
        when (kindOfCoffee) {
            1 -> {
                water -= WATER_FOR_ESPRESSO
                coffee -= COFFEE_FOR_ESPRESSO
                disposableCups -= 1
                money += MONEY_FOR_ESPRESSO
            }
            2 -> {
                water -= WATER_FOR_LATTE
                milk -= MILK_FOR_LATTE
                coffee -= COFFEE_FOR_LATTE
                disposableCups -= 1
                money += MONEY_FOR_LATTE
            }
            3 -> {
                water -= WATER_FOR_CAPPUCCINO
                milk -= MILK_FOR_CAPPUCCINO
                coffee -= COFFEE_FOR_CAPPUCCINO
                disposableCups -= 1
                money += MONEY_FOR_CAPPUCCINO
            }
        }
    }

    private fun checkSupplyBeforeMakingCoffee(kindOfCoffee: Int): Boolean {
        var enoughWater = true
        var enoughMilk = true
        var enoughCoffee = true
        var enoughCups = true

        when (kindOfCoffee) {
            1 -> {
                if (water < WATER_FOR_ESPRESSO) enoughWater = false
                if (coffee < COFFEE_FOR_ESPRESSO) enoughCoffee = false
                if (disposableCups < 1) enoughCups = false
            }

            2 -> {
                if (water < WATER_FOR_LATTE) enoughWater = false
                if (milk < MILK_FOR_LATTE) enoughMilk = false
                if (coffee < COFFEE_FOR_LATTE) enoughCoffee = false
                if (disposableCups < 1) enoughCups = false
            }

            3 -> {
                if (water < WATER_FOR_CAPPUCCINO) enoughWater = false
                if (milk < MILK_FOR_CAPPUCCINO) enoughMilk = false
                if (coffee < COFFEE_FOR_CAPPUCCINO) enoughCoffee = false
                if (disposableCups < 1) enoughCups = false
            }
        }

        if (!enoughWater) println("Sorry, not enough water!")
        if (!enoughMilk) println("Sorry, not enough milk!")
        if (!enoughCoffee) println("Sorry, not enough coffee!")
        if (!enoughCups) println("Sorry, not enough disposable cups!")

        return enoughWater && enoughMilk && enoughCoffee && enoughCups
    }

    fun buy() {
        println()
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
        val buyInput = readln()

        if (buyInput == "back") return

        val kindOfCoffee = buyInput.toInt()
        if (checkSupplyBeforeMakingCoffee(kindOfCoffee) && kindOfCoffee in 1..3) {
            println("I have enough resources, making you a coffee!")
            makeCoffee(kindOfCoffee)
        }

    }

}

fun main() {

    while (CoffeeMachine.askAction()) {
        when (CoffeeMachine.currentAction) {
            MachineAction.BUY -> CoffeeMachine.buy()
            MachineAction.TAKE -> CoffeeMachine.take()
            MachineAction.FILL -> CoffeeMachine.fill()
            MachineAction.REMAINING -> CoffeeMachine.printSupply()
        }
        println()
    }

}
