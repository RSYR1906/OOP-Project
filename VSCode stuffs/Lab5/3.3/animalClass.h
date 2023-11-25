#ifndef ANIMAL_CLASS_H
#define ANIMAL_CLASS_H

#include <string>

enum COLOR
{
    Green,
    Blue,
    White,
    Black,
    Brown
};

class Animal
{
public:
    Animal();
    Animal(std::string n, COLOR c);
    virtual ~Animal();
    virtual void speak() = 0;
    virtual void move() = 0; // Pure virtual function

protected:
    std::string _name;
    COLOR _color;
};

class Mammal : public Animal
{
public:
    Mammal();
    Mammal(std::string n, COLOR c);
    virtual void eat() const;
    void move() override;
};

#endif // ANIMAL_CLASS_H
