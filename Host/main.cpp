#include <QCoreApplication>
#include "server.h"

int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);

    new Server("192.168.240.112", "ttyUSB1", &a);

    return a.exec();
}
