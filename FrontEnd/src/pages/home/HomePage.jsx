import styles from '../../styles/HomePageStyles.module.css';
import Card from 'react-bootstrap/Card';
import { Image } from "react-bootstrap";
import { useState } from "react";
import { useLocation } from "react-router-dom";

function HomePage() {
    const location = useLocation();
    const [booksByTitle, setBooksByTitle] = useState(null);
    const [booksByAuthor, setBooksByAuthor] = useState(null);
    const [isLoadingBooksByAuthor, setisLoadingBooksByAuthor] = useState(false);
    const [isLoadingBooksByTitle, setisLoadingBooksByTitle] = useState(false);
    const [isBookByAuthorStateIdle, setisBookByAuthorStateIdle] = useState(true);
    const [isBookByTitleStateIdle, setisBookByTitleStateIdle] = useState(true);
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
      const [errorFetchingBooksByAuthor, setErrorFetchingBooksByAuthor] = useState(null);
      const [errorFetchingBooksByTitle, setErrorFetchingBooksByTitle] = useState(null);

    const fetchBooksByAuthor = async () => {
        console.log("fetchBooksByAuthor");
        try {
            setisBookByAuthorStateIdle(false);
            setisLoadingBooksByAuthor(true);
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
            console.log(data);
            setBooksByAuthor(data);
            setisLoadingBooksByAuthor(false);
        } catch (error) {
            setErrorFetchingBooksByAuthor(error);
        }
    };

    const fetchBooksByTitle = async () => {
        console.log("fetchBooksByTitle");
        try {
            setisBookByTitleStateIdle(false);
            setisLoadingBooksByTitle(true);
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
            setisLoadingBooksByTitle(false);
        } catch (error) {
            setErrorFetchingBooksByTitle(error);
        }
    };


    return (

        
        <div className={styles.homePageContainer}>
            <h1>Welcome to MyBookPlace, {location.state?.username}</h1>
            <p>Search for your favorite books here!</p>
            <h2 className={styles.bookByAutor}>Book by Author</h2>
            <form onSubmit={(e) => { e.preventDefault(); fetchBooksByAuthor(); } }>
                <div className='textField'>

                    <input
                        type="text"
                        name="author"
                        placeholder="Search Book by Author"
                        value={author}
                        onChange={(e) => setAuthor(e.target.value)} />
                </div>

                <button type='submit'>Search book by author</button>
            </form>
                <div>
                    {isBookByAuthorStateIdle ? (
                        <p>Waiting...</p>
                    ) : isLoadingBooksByAuthor ? (
                        <p>Loading items...</p>
                    ) : booksByAuthor ? (
                        <Card className={styles.resultContainer}>
                            <Card.Img as={Image} variant="top" src={booksByAuthor.thumbnail}/>
                            <Card.Body>
                                <Card.Title style={{marginTop: '10px', marginBottom: '2px', fontSize: '30px', fontWeight: 'bold'}}>{booksByAuthor.title}</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted" style={{marginBottom:'30px'}}>{booksByAuthor.subtitle}</Card.Subtitle>
                                <Card.Text>
                                    {booksByAuthor.description}
                                </Card.Text>
                            </Card.Body>
                        <Card.Body>
                            <Card.Text style={{marginTop: '20px', fontSize:'14px', fontWeight: 'bold'}}>Authors:</Card.Text>
                            <Card.Text style={{fontSize: '14px'}}>{booksByAuthor.authors?.join(', ')}</Card.Text>
                            
                            </Card.Body>
                        </Card> 
                    ) 
           
             : errorFetchingBooksByAuthor ? (
            <p>Error searching for book: {errorFetchingBooksByAuthor.message}</p>
            ) :
            (
            <p>Couldnt find book by this author</p>
            )}
        </div><h2 className={styles.bookByAutor}>Book by Title</h2><form onSubmit={(e) => { e.preventDefault(); fetchBooksByTitle(); } }>
                <input className='textField'
                    type="text"
                    name="title"
                    placeholder="Search Book by Title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)} />

                <button type='submit'>Search book by title</button>
            </form><div>
                {isBookByTitleStateIdle ? (
                    <p>Waiting...</p>
                ) : isLoadingBooksByTitle ? (
                    <p>Loading items...</p>
                ) : booksByTitle ? (
                    // <div className={styles.bookItem}>
                    //     <h3>{booksByTitle.title}</h3>
                    //     <p>Subtitle: {booksByTitle.subtitle}</p>
                    //     <p>Publisher: {booksByTitle.editora}</p>
                    //     <p>Authors: {booksByTitle.authors?.join(', ')}</p>
                    //     <p>Description: {booksByTitle.description}</p>
                    //     {booksByTitle.thumbnail && <img src={booksByTitle.thumbnail} alt="Capa do Livro" />}
                    // </div>
                    <Card className={styles.resultContainer}>
                            <Card.Img as={Image} variant="top" src={booksByTitle.thumbnail}/>
                            <Card.Body>
                                <Card.Title style={{marginTop: '10px', marginBottom: '2px', fontSize: '30px', fontWeight: 'bold'}}>{booksByTitle.title}</Card.Title>
                                <Card.Subtitle className="mb-2 text-muted" style={{marginBottom:'30px'}}>{booksByTitle.subtitle}</Card.Subtitle>
                                <Card.Text>
                                    {booksByTitle.description}
                                </Card.Text>
                            </Card.Body>
                        <Card.Body>
                            <Card.Text style={{marginTop: '20px', fontSize:'14px', fontWeight: 'bold'}}>Authors:</Card.Text>
                            <Card.Text style={{fontSize: '14px'}}>{booksByTitle.authors?.join(', ')}</Card.Text>
                            
                            </Card.Body>
                        </Card> 
                ) : errorFetchingBooksByTitle ? (
                    <p>Error searching for book: {errorFetchingBooksByTitle.message}</p>
                )
                    : (
                        <p>Couldnt find book with this title</p>
                    )}
            </div>
            
        </div>
    );
}

export default HomePage;