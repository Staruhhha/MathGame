package com.example.mathgame

class MathExpressionGenerator {

    fun generateNumbers() : String{
        var expression = ""
        while (true){
            when((1..4).random()){
                1 -> expression = singleTermsExpression()
                2 -> expression = doubleTermsExpression()
                3 -> expression = tripleTermsExpression()
                4 -> expression = fourthTermsExpression()
            }
            if (CalculateClass().evaluate(expression)!! % 1.0 == 0.0 && CalculateClass().evaluate(expression)!! <= 100.0 && CalculateClass().evaluate(expression)!! > 0.0) break
        }
        return expression
    }


    private fun fourthTermsExpression() : String {
        var expression = ""
        val firstTerm = (1..20).random()
        val secondTerm = (1..20).random()
        val thirdTerm = (1..20).random()
        val fourhtTerm = (1..20).random()
        val operators : ArrayList<String> = ArrayList()

        for (i in 1..3){
            when((1..4).random()){
                1 -> operators.add("+")
                2 -> operators.add("-")
                3 -> operators.add("*")
                4 -> operators.add("/")

            }
        }
        expression = "(($firstTerm${operators.get(0)}$secondTerm)${operators.get(1)}$thirdTerm)${operators.get(2)}$fourhtTerm"
        return expression
    }

    private fun tripleTermsExpression() : String {
        var expression = ""
        val firstTerm = (1..20).random()
        val secondTerm = (1..20).random()
        val thirdTerm = (1..20).random()
        var operators : ArrayList<String> = ArrayList()

        for (i in 1..2){
            when((1..4).random()){
                1 -> operators.add("+")
                2 -> operators.add("-")
                3 -> operators.add("*")
                4 -> operators.add("/")

            }
        }
        expression = "($firstTerm${operators.get(0)}$secondTerm)${operators.get(1)}$thirdTerm"
        return expression
    }

    private fun doubleTermsExpression() : String {
        var expression = ""
        val firstTerm = (1..20).random()
        val secondTerm = (1..20).random()
        when((1..4).random()){
            1 -> expression = "$firstTerm+$secondTerm"
            2 -> expression = "$firstTerm-$secondTerm"
            3 -> expression = "$firstTerm*$secondTerm"
            4 -> expression = "$firstTerm/$secondTerm"

        }
        return expression
    }

    private fun singleTermsExpression() : String {
        val singleExpression = (1..20).random()
        return singleExpression.toString()
    }

}