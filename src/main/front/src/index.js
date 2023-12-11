import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';

import App from './components/App';

import 'bootstrap/dist/css/bootstrap.min.css';
import './styles/common.css';

ReactDOM
	.createRoot(document.getElementById('root'))
	.render(
	  <BrowserRouter>
	  	<App />
	  </BrowserRouter>
	);
