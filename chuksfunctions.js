const power = (v,r) => {
    let total =1
    for (let i=0; i<r; i++){
        total *=v
    } return total
}

//HO function. takes in an anon function with param 'radix'
//it returns a function that takes in a value -> function(value)
const mkPower = (radix) =>{
    return (value) => { 
        return power(value,radix) //// Uses 'radix' from the outer scope
    }
}
// The mkPower function is a higher-order function because it returns another function. 
// This inner function captures and retains the radix parameter from the outer function thanks to a closure. 
// closure-> an inner function remembers the variables from its parent function's scope even after the parent function has finished execution.

console.log(">>>power (2,3)", power(2,3))
console.log(">>>power (3,3)", power(3,3))

//square is a function RETURNED BY mkPower that uses 2 as the radix for mkpower
const square = mkPower(2)
//When you call mkPower(2), you're creating a new function where radix is set to 2. 
//This new function is stored in the square variable.

const cube = mkPower(3)
//when we call square(4),we invoke the function that was returned by mkPower(2). Here's the process:
// /1. mkPower(2) returns a new function, essentially equivalent to:
            //(value) => {
            //     return power(value, 2);
            // }
//This function is what square references.
//2/ Calling square(4) is equivalent to calling this returned function with 4 as the value argument, effectively calling power(4, 2).
//3 The power function  is then called with 4 as the base (value) and 2 as the exponent (radix), 
//which were captured by the closure in the function returned by mkPower

//the function returned by mkPower closes over (or captures) the radix argument from mkPower's execution context. 
//This means that even after mkPower finishes executing, the returned function still has access to the radix value it was created with. 
//When you later call square(4), JavaScript knows to use 4 as the value because square references a function that expects one argument, 
//and within that function's body, value is used as the base in the power(value, radix) call.


console.log(">>>mkPower(2)", square()) // returns Nan because in (value, radix), value is null 
console.log(">>>square (4)", square(4)) //4 to the power of 2
console.log(">>>cube (4)", cube(4)) // 4 to the power of 3

console.log(">>>square ()", square(4)) //4 to the power of 2

//if we just reference square without parenthesis
//console.log(">>>mkPower(2)", square); 
// This line will log the function definition that square references. 
//It does not execute the function. 
//it shows something like function (value) { return power(value, 2); } in the console, indicating that square is a function waiting to be called.