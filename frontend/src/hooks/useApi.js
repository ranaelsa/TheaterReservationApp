import { useState } from 'react';
import axios from 'axios';

const useApi = (endpoint, method = 'GET') => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [data, setData] = useState(null);

    const callApi = async (body = null) => {
        setLoading(true);
        setError(null);

        try {
            const response = await axios({
                url: endpoint,
                method,
                data: body,
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setData(response.data);
            return response.data;
        } catch (err) {
            setError(err.response?.data?.message || err.message || 'Something went wrong');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return { callApi, data, loading, error };
};

export default useApi;

