import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Header from "./components/Header/Header.jsx";
import Main from "./pages/Main.jsx";
import Create from "./pages/Create.jsx";
import List from "./pages/List.jsx";
import Footer from "./components/Footer/Footer.jsx";

import 'antd/dist/antd.css';

const App = () => {
  return (
      <Router>
        <Header />
        <Routes>
          <Route exact path="/" element={<Main />} />
          <Route path="/main" element={<Main />} />
          <Route path="/create" element={<Create />} />
          <Route path="/list" element={<List />} />
        </Routes>
        <Footer />
      </Router>
  );
};

export default App
