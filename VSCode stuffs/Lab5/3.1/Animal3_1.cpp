#include <iostream>
#include <string>
using namespace std;

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
    Animal() : _name("unknown"), _color(Green)
    {
        cout << "Constructing Animal object " << _name << endl;
    }
    Animal(string n, COLOR c)
    {
        string colors[5] = {"green", "blue", "white", "black", "brown"};
        _name = n;
        _color = c;
        cout << "Constructing animal " << _name << " with color " << colors[c] << endl;
    }
    ~Animal()
    {
        cout << "Destructing Animal object named " << _name << endl;
    }
    void speak()
    {
        cout << "Animal speaks " << endl;
    }
    void move()
    {
        cout << "Animal moves " << endl;
    }

protected:
    string _name;
    COLOR _color;
};

int main() {
Animal a("Animal", Blue);
a.speak() ;
 cout << "Program exiting ... "<< endl ;
return 0;
}
