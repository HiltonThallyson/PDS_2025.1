import styles from '../../styles/HomePageStyles.module.css';
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

function HomePage() {
    const location = useLocation();
    const [booksByTitle, setBooksByTitle] = useState(null);
    const [booksByAuthor, setBooksByAuthor] = useState(null);
    const [isLoading, setisLoading] = useState(false);
    const [isIdle, setisIdle] = useState(true);
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    //   const [error, setError] = useState(null);

    const fetchBooksByAuthor = async () => {
        console.log("fetchBooksByAuthor");
        try {
            setisIdle(false);
            setisLoading(true);
            const response = await fetch(`http://localhost:8090/api/livros/autor?autor=${author}`, {
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
            setBooksByAuthor(data);
            setisLoading(false);
            console.log(typeof data); // Deve mostrar "object"
            console.log(data);
        } catch (error) {
            console.error('Erro ao buscar livros:', error);
            // setError(error);
        }
    };

    const fetchBooksByTitle = async () => {
        console.log("fetchBooksByTitle");
        try {
            setisIdle(false);
            setisLoading(true);
            const response = await fetch(`http://localhost:8090/api/livros/titulo?titulo=${title}`, {
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
            setBooksByTitle(data);
            setisLoading(false);
            console.log(typeof data); // Deve mostrar "object"
            console.log(data);
        } catch (error) {
            console.error('Erro ao buscar livros:', error);
            // setError(error);
        }
    };


    return (
        <div className={styles.homePageContainer}>
            <h1>Bem-vindo(a) ao MyBookPlace, {location.state?.username}</h1>
            <p>Faça sua pesquisa abaixo!</p>
            <h2 className={styles.bookByAutor}>Livro por autor</h2>
            <form onSubmit={(e) => {e.preventDefault();fetchBooksByAuthor();}}>      
                <input 
                    type="text"
                    name="author"
                    placeholder="Pesquisar Autor"
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                />

            <button type='submit'>Buscar Livro Por Autor</button>
            </form>
            <div>
                {isIdle ? (
                    <p>Aguardando solicitação</p>
                ) : isLoading ? (
                    <p>Loading items...</p>
                ) : booksByAuthor ? ( 
                    <div className={styles.bookItem}>
                        <h3>{booksByAuthor.title}</h3>
                        <p>Subtítulo: {booksByAuthor.subtitle}</p>
                        <p>Editora: {booksByAuthor.editora}</p>
                        <p>Autor(es): {booksByAuthor.authors?.join(', ')}</p> 
                        <p>Descrição: {booksByAuthor.description}</p>
                        {booksByAuthor.thumbnail && <img src={booksByAuthor.thumbnail} alt="Capa do Livro" />} 
                    </div>
                ) : (
                    <p>Nenhum livro encontrado para este autor.</p>
                )}
            </div>
            <h2 className={styles.bookByAutor}>Livro por título</h2>
            <form onSubmit={(e) => {e.preventDefault();fetchBooksByTitle();}}>      
                <input 
                    type="text"
                    name="title"
                    placeholder="Pesquisar Título"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                />

            <button type='submit'>Buscar Livro Por Título</button>
            </form>
            <div>
                {isIdle ? (
                    <p>Aguardando solicitação</p>
                ) : isLoading ? (
                    <p>Loading items...</p>
                ) : booksByTitle ? ( 
                    <div className={styles.bookItem}>
                        <h3>{booksByTitle.title}</h3>
                        <p>Subtítulo: {booksByTitle.subtitle}</p>
                        <p>Editora: {booksByTitle.editora}</p>
                        <p>Autor(es): {booksByTitle.authors?.join(', ')}</p> 
                        <p>Descrição: {booksByTitle.description}</p>
                        {booksByTitle.thumbnail && <img src={booksByTitle.thumbnail} alt="Capa do Livro" />} 
                    </div>
                ) : (
                    <p>Nenhum livro encontrado para este título.</p>
                )}
            </div>
            
        </div>
    );
}

export default HomePage;