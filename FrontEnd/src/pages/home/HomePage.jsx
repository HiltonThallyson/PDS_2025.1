import styles from '../../styles/HomePageStyles.module.css';
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

function HomePage() {
    const location = useLocation();
    const [books, setBooks] = useState(null); // Inicializa como null, já que será um objeto
    const [isLoading, setisLoading] = useState(false);
    const [isIdle, setisIdle] = useState(true);
    //   const [error, setError] = useState(null);

    const fetchBooksByAuthor = async (autor) => {
        try {
            setisIdle(false);
            setisLoading(true);
            const response = await fetch(`http://localhost:8090/api/livros/autor?autor=${autor}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    //   'Authorization': 'Bearer ' + jwtStr
                },
            });
            if (!response.ok) {
                throw new Error('Falha ao buscar livros');
            }
            const data = await response.json();
            setBooks(data);
            setisLoading(false);
            console.log(typeof data); // Deve mostrar "object"
            console.log(data);
        } catch (error) {
            console.error('Erro ao buscar livros:', error);
            // setError(error);
        }
    };

    // useEffect(() => {
    //     fetchBooksByAuthor("tolkien");
    // }, []);

    return (
        <div className={styles.homePageContainer}>
            <h1>Welcome to MyBookPlace, {location.state?.username}</h1>
            <p>This is the main content of the home page.</p>
            <h2 className={styles.bookByAutor}>Book by Author</h2>
            <div>
                {isIdle ? (
                    <p>Aguardando solicitação</p>
                ) : isLoading ? (
                    <p>Loading items...</p>
                ) : books ? ( 
                    <div className={styles.bookItem}>
                        <h3>{books.title}</h3>
                        <p>Subtítulo: {books.subtitle}</p>
                        <p>Editora: {books.editora}</p>
                        <p>Autor(es): {books.authors?.join(', ')}</p> 
                        <p>Descrição: {books.description}</p>
                        {books.thumbnail && <img src={books.thumbnail} alt="Capa do Livro" />} 
                    </div>
                ) : (
                    <p>Nenhum livro encontrado para este autor.</p>
                )}
            </div>
            <button onClick={() => fetchBooksByAuthor("monteiro lobato")}>Buscar Livro Por Autor</button>
        </div>
    );
}

export default HomePage;