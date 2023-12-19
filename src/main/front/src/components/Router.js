import { Route, Routes, } from 'react-router-dom';

import Home from './Home';
import Login from './Login';
import Join from './Join';
import Setting from './Setting';
import Member from './Member';

function Router() {
  return (
	  <>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route path="/setting" element={<Setting />} />
        <Route path="/member" element={<Member />} />
      </Routes>
      </>
  );
}

export default Router;