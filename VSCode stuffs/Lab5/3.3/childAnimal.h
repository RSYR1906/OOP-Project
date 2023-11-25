#ifndef CHILD_ANIMAL_H
#define CHILD_ANIMAL_H

#include "animalClass.h"

class Dog : public Mammal
{
public:
    Dog();
    Dog(std::string n, COLOR c, std::string o);
    void speak() override;
    void move() override;

private:
    std::string _owner;
};

class Cat : public Mammal
{
public:
    Cat();
    Cat(std::string n, COLOR c);
    void move() override;
    void speak() override;
};

class Lion : public Mammal
{
public:
    Lion();
    Lion(std::string n, COLOR c);
    void move() override;
    void speak() override;
};

#endif // CHILD_ANIMAL_H
