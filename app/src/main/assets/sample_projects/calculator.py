# Simple Calculator - Sample Python Script
# This file demonstrates functions and user interaction concepts

def add(a, b):
    return a + b

def subtract(a, b):
    return a - b

def multiply(a, b):
    return a * b

def divide(a, b):
    if b != 0:
        return a / b
    else:
        return "Error: Division by zero"

# Example usage
print("=== Python Calculator ===")
print()

num1 = 15
num2 = 3

print(f"Numbers: {num1} and {num2}")
print()
print(f"Addition: {num1} + {num2} = {add(num1, num2)}")
print(f"Subtraction: {num1} - {num2} = {subtract(num1, num2)}")
print(f"Multiplication: {num1} * {num2} = {multiply(num1, num2)}")
print(f"Division: {num1} / {num2} = {divide(num1, num2)}")

# Factorial example
def factorial(n):
    if n <= 1:
        return 1
    return n * factorial(n - 1)

print()
print(f"Factorial of 5 = {factorial(5)}")
