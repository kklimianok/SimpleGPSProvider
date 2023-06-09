#include "server.h"

Server::Server(QString host, QString device, QObject *parent) : QObject(parent)
{
    this->host = host;
    this->device = device;
    socket = new QTcpSocket(this);
    connect(socket, SIGNAL(connected()), this, SLOT(connected()));
    connect(socket, SIGNAL(disconnected()), this, SLOT(disconnected()));
    connect(socket, SIGNAL(errorOccurred(QAbstractSocket::SocketError)), this, SLOT(socketError(QAbstractSocket::SocketError)));
    serial.setPortName(device);
    serial.setBaudRate(QSerialPort::Baud115200);
    connect(&serial, SIGNAL(readyRead()), this, SLOT(sendData()));
    connect(&serial, SIGNAL(errorOccurred(QSerialPort::SerialPortError)), this, SLOT(serialError(QSerialPort::SerialPortError)));
    socket->connectToHost(host, 5897, QIODevice::WriteOnly);
}

void Server::sendData()
{
    while (!serial.atEnd()) {
        if (!socket->isOpen() || !socket->isWritable()) {
            qDebug("Socket is not available. Can't write.");
            serial.readAll();
            return;
        }
        if (serial.canReadLine()) {
            QByteArray data = serial.readLine();
            if (data.isEmpty()) {
                continue;
            }
            socket->write(data);
            qDebug("data sent");
        }
    }
}

void Server::connected()
{
    qDebug("Socket connected.");
    if (!serial.open(QIODevice::ReadOnly)) {
        qDebug("Couldn't read device.");
    }
}

void Server::disconnected()
{
    qDebug("Socket disconnected.");
    serial.close();
}

void Server::socketError(QAbstractSocket::SocketError error)
{
    qDebug("%s", QString(error).toStdString().c_str());
    thread()->sleep(5);
    socket->connectToHost(host, 5897, QIODevice::WriteOnly);
}

void Server::serialError(QSerialPort::SerialPortError error)
{
    qDebug("%s", QString(error).toStdString().c_str());
    //TODO implement
}
