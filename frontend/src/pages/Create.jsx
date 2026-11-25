import React from 'react';
import { Button, Form, Input, Select } from 'antd';
import PhoneMaskedInput from "../components/PhoneMaskedInput/PhoneMaskedInput.jsx";

const Create = () => {

    const handleSubmit = async (formData) => {
        try {
            const response = await fetch('/api/doctor-appointment/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData),
            });

            const data = await response.json();
            if ('id' in data) {
                alert(`Запись успешно создана, ID: ${data.id}`);
                window.location.reload();
            } else {
                alert('Возникла ошибка при создании записи.');
            }
        } catch (err) {
            console.error('Ошибка:', err);
            alert('Что-то пошло не так. Пожалуйста, попробуйте позже.');
        }
    };

    return (
        <main>
            <article>
                <section>
                    <h2>Оставьте заявку</h2>
                    <h3>Наш специалист свяжется с вами в течение дня.</h3>
                    <Form layout="vertical" onFinish={handleSubmit}>
                        <Form.Item label="Имя:" name="patientName" rules={[{ required: true,  message: 'Пожалуйста, введите Ваше имя.'}]}>
                            <Input />
                        </Form.Item>
                        <Form.Item label="Телефон:" name="patientPhone" rules={[{ required: true, message: 'Пожалуйста, введите Ваш контактный телефон.' }]}>
                            <PhoneMaskedInput name="patientPhone"/>
                        </Form.Item>
                        <Form.Item label="Электронная почта:" name="patientEmail" rules={[{ required: true, message: 'Пожалуйста, введите Ваш email.' }]}>
                            <Input type="email" />
                        </Form.Item>
                        <Form.Item label="Специализация врача:" name="doctorSpecialization" rules={[{ required: true, message: 'Пожалуйста, укажите специальность врача к которому хотите обратиться.' }]}>
                            <Select>
                                <Select.Option value="Терапевт">Терапевт</Select.Option>
                                <Select.Option value="Хирург">Хирург</Select.Option>
                                <Select.Option value="Отоларинголог">Отоларинголог</Select.Option>
                            </Select>
                        </Form.Item>
                        <Button type="primary" htmlType="submit">Отправить</Button>
                    </Form>
                    <p>Нажимая кнопку “Отправить”, вы соглашаетесь с политикой конфиденциальности.<br />
                        Запись через сайт является предварительной.<br />
                        Администратор свяжется с Вами для подтверждения записи к специалисту.</p>
                </section>
            </article>
        </main>
    );
};

export default Create;