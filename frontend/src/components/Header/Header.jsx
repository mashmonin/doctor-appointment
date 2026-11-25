import React from 'react';
import { Link } from 'react-router-dom';
import { Tabs } from 'antd';
import styles from './Header.module.css';


const Header = () => {
    const tabsConfig = [
        {
            label: <Link to="/main">Главная</Link>,
            key: '1',
        },
        {
            label: <Link to="/create">Записаться на прием</Link>,
            key: '2',
        },
        {
            label: <Link to="/list">Мои записи</Link>,
            key: '3',
        },
    ];

    return (
        <header className={styles.header}>
            <img className="logopic" src={`/images/logopic.png`} alt="Logo" />
            <h1>Lavamed - burning infections like lava</h1>
            <div className="tabs-container">
                <Tabs defaultActiveKey="1" items={tabsConfig} />
            </div>
        </header>
    );
};

export default Header;