#ifndef SERVER_H
#define SERVER_H

#include <QTcpSocket>
#include <QObject>
#include <QList>
#include <QSerialPort>
#include <QThread>
#include <QDebug>

class Server : public QObject
{
    Q_OBJECT
public:
    explicit Server(QString host, QString device, QObject *parent = nullptr);

private slots:
    void connected();
    void disconnected();
    void sendData();
    void socketError(QAbstractSocket::SocketError error);
    void serialError(QSerialPort::SerialPortError error);

private:
    QSerialPort serial;
    QTcpSocket *socket;
    QString host;
    QString device;
};

#endif // SERVER_H
