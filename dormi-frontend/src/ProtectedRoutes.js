import {
  Navigate, Outlet, useLocation,
} from 'react-router-dom';
import React from 'react';
import { hasJwtValid, getRoles } from './context/jwtAuth';
const ProtectedRoute = ({
  children, roles
}) => {
  const redirectPath = '/';
  console.log(roles);
  const location = useLocation();
  const validated = hasJwtValid();
  const jwtRoles = getRoles();
  console.log(jwtRoles);
  // const contain=jwtRoles.forEach(role => {
  //   if(roles.includes(role)) return true;
  // });
  if (!validated && jwtRoles.find(role => roles.includes(role))) {
    return <Navigate to={redirectPath} replace state={{ from: location }} />;
  }

  return children || <Outlet />;
}

export default ProtectedRoute;