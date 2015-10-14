#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#else
#include "WProgram.h"
#endif
#include "bitlash.h"
#include "Servo.h"

Servo leftServo;
Servo rightServo;
const int trigPin = 2;
const int echoPin = 4;

const int leftLED = 3;
const int rightLED = 5;
boolean obstacle;

numvar forward_handler(void) {
  int seconds = map(getarg(1), 0, 1000, 0, 1000000);
  moveForward();
  delay(seconds);
  halt();
  return 0;
}

numvar back_handler(void) {
  int seconds = map(getarg(1), 0, 1000, 0, 1000000);
  moveBackward();
  delay(seconds);
  halt();
  return 0;
}

numvar left_handler(void) {
  int degs = map(getarg(1), 0, 360, 0, 2260);
  turnLeft();
  delay(degs);
  halt();
  return 0;
}

numvar right_handler(void) {
  int degs = map(getarg(1), 0, 360, 0, 2260);
  turnRight();
  delay(degs);
  halt();
  return 0;
}

numvar left_light_handler(void) {
  for (int i = 0; i < getarg(1); i++) {
    digitalWrite(leftLED, HIGH);
    delay(200);
    digitalWrite(leftLED, LOW);
    delay(200); 
  }
  return 0;
}

numvar right_light_handler(void) {
  for (int i = 0; i < getarg(1); i++) {
    digitalWrite(rightLED, HIGH);
    delay(200);
    digitalWrite(rightLED, LOW);
    delay(200); 
  }
  return 0;
}

numvar obstacle_handler(void) {
  int withinInches = getarg(1);
  long duration, inches;
  
  pinMode(trigPin, OUTPUT);
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  pinMode(echoPin, INPUT);
  duration = pulseIn(echoPin, HIGH);
  inches = duration / 74 / 2;
  if (inches < withinInches) {
    obstacle = true;
    return 1;
  } 
  obstacle = false;
  return 0;
}

void setup(void) {
  obstacle = false;
  leftServo.attach(12);
  rightServo.attach(13);
  pinMode(rightLED, OUTPUT);
  pinMode(leftLED, OUTPUT);
  initBitlash(9600);
  addBitlashFunction("mf", (bitlash_function) forward_handler);
  addBitlashFunction("mb", (bitlash_function) back_handler);
  addBitlashFunction("tl", (bitlash_function) left_handler);
  addBitlashFunction("tr", (bitlash_function) right_handler);
  addBitlashFunction("ll", (bitlash_function) left_light_handler);
  addBitlashFunction("rl", (bitlash_function) right_light_handler);
  addBitlashFunction("oo", (bitlash_function) obstacle_handler);
}

void loop(void) {
  runBitlash();
}

void moveForward() {
  rightServo.write(180);
  leftServo.write(0);
}

void moveBackward() {
  rightServo.write(0);
  leftServo.write(180);
}

void turnLeft() {
  rightServo.write(0);
  leftServo.write(0);
}

void turnRight() {
  rightServo.write(180);
  leftServo.write(180);
}

void halt() {
  leftServo.write(90);
  rightServo.write(90);
}
