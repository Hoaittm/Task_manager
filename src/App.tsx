
import { Provider } from 'react-redux';
import { store } from './redux/store';
import { BrowserRouter } from 'react-router-dom';
import ClientRouter from './routers/clientRouter';

function App() {
  return (
    <Provider store={store}>
      <BrowserRouter>
   
      <ClientRouter/>
         </BrowserRouter>
    </Provider>
  );
}

export default App;
