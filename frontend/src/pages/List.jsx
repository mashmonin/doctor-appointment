import React, { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import axios from 'axios';
import { Collapse, Button, Pagination, Input, DatePicker } from 'antd';

const { Panel } = Collapse

const List = () => {
    const [currentPage, setCurrentPage] = useState(1);
    const [totalRecords, setTotalRecords] = useState(0);
    const [data, setData] = useState([]);
    const [filters, setFilters] = useState({});
    const pageSize = 10;

    const fetchData = async () => {
        console.log("Переданные фильтры:", filters);
        try {
            const response = await axios.post(
                `/api/doctor-appointment/list?page=${currentPage - 1}&size=${pageSize}`,
                filters
            );

            if (response.status === 200 && response.data && response.data.content) {
                setData(response.data.content || []); // Устанавливаем полученные данные
                setTotalRecords(response.data.totalElements || 0); // Определяем общее число страниц
            } else {
                console.warn('Некорректный ответ сервера');
            }
        } catch (err) {
            console.error('Ошибка загрузки данных:', err.message);
            console.error(err.response ? err.response.data : null); // Полностью выводим ответ сервера
        }
    };

    // useEffect это hook, который будет следить за изменениями фильтров и страницы пагинации
    useEffect(() => {
        fetchData(); // Получаем данные при монтировании компонента и при изменении фильтров или страницы пагинации
    }, [currentPage]);

    const handleFilterChange = (field, value) => {
        setFilters(prev => ({
            ...prev,
            [field]: value
        }));
    };

    const handleStartDateChange = (_, dateString) => {
        setFilters(prev => ({
            ...prev,
            creationDate: { ...prev.creationDate, start: dateString || null },
        }));
    };

    const handleEndDateChange = (_, dateString) => {
        setFilters(prev => ({
            ...prev,
            creationDate: { ...prev.creationDate, end: dateString || null },
        }));
    };

    const applyFilters = () => {
        if (currentPage === 1) {
            fetchData();
        } else {
            setCurrentPage(1); // useEffect сработает и вызовет fetchData
        }
    };

    const handlePageChange = (newPage) => {
        setCurrentPage(newPage); // Переключаемся на другую страницу
    };

    return (
        <main>
            <article>
                <section>
                <h2>Список записей</h2>
                <Collapse ghost>
                    <Panel header="Фильтры" key="1">
                        <div className="panel-grid">
                            <div className="filter-bar">
                                <div className="text-input">
                                    <label>Текст:</label>
                                    <Input
                                        className="text-input"
                                        placeholder="ФИО или специализация"
                                        type="text"
                                        value={filters.text || ''}
                                        onChange={e => handleFilterChange('text', e.target.value)}
                                    />
                                </div>
                                <div className="datepicker-wrapper">
                                    <DatePicker
                                        placeholder="Начало периода"
                                        value={filters.creationDate?.start ? dayjs(filters.creationDate.start) : null}
                                        onChange={handleStartDateChange}
                                        format="YYYY-MM-DD"
                                        showTime={false}
                                    />
                                </div>
                                <div className="datepicker-wrapper">
                                    <DatePicker
                                        placeholder="Конец периода"
                                        value={filters.creationDate?.end ? dayjs(filters.creationDate.end) : null}
                                        onChange={handleEndDateChange}
                                        format="YYYY-MM-DD"
                                        showTime={false}
                                    />
                                </div>
                                <Button className="search-button" type="primary" onClick={applyFilters}>
                                    Найти
                                </Button>
                            </div>
                        </div>
                    </Panel>
                </Collapse>
                <table id="patientsTable" className="records-table">
                    <thead>
                    <tr>
                        <th>№</th>
                        <th>ФИО</th>
                        <th>Телефон</th>
                        <th>Почта</th>
                        <th>Специализация врача</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.map((item, idx) => (
                        <tr key={idx}>
                            <td>{item.id}</td>
                            <td>{item.patientName}</td>
                            <td>{item.patientPhone}</td>
                            <td>{item.patientEmail}</td>
                            <td>{item.doctorSpecialization}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <Pagination
                    current={currentPage}
                    total={totalRecords}
                    pageSize={pageSize}
                    onChange={handlePageChange}
                />
                </section>
            </article>
        </main>
    );
};

export default List;