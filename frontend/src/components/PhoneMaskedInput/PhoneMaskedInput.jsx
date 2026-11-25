import React from 'react';
import { MaskedInput } from 'antd-mask-input';

const PhoneMaskedInput = ({ onChange }) => {
    return (
        <MaskedInput
            mask="+7(000)000-00-00"
            unmasked={false}
            lazy={false}
            onChange={onChange}
        />
    );
};

export default PhoneMaskedInput;

