import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';
import { environment } from '../environments/environment';
import { UserResponse } from '../responses/user/user.response';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private apiRegister = `${environment.apiBaseUrl}/users/register`;
  private apiLogin = `${environment.apiBaseUrl}/users/login`;
  private apiUserDetail = `${environment.apiBaseUrl}/users/details`;
  private apiConfig = {
    headers: this.createHeaders(),
  };

  constructor(private http: HttpClient) {}
  private createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept-Language': 'vi',
    });
  }

  register(registerDTO: RegisterDTO): Observable<any> {
    return this.http.post(this.apiRegister, registerDTO, this.apiConfig);
  }

  login(loginDto: LoginDTO): Observable<any> {
    return this.http.post(this.apiLogin, loginDto, this.apiConfig);
  }

  // getUserDetail(token: string) {
  //   return this.http.post(this.apiUserDetail, {
  //     headers: new HttpHeaders({
  //       'Content-Type': 'application/json',
  //       Authorization: `Bearer ${token}`,
  //     }),
  //   });
  // } 403
  getUserDetail(token: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    });
    return this.http.post(this.apiUserDetail, {}, { headers });
  }

  saveUserToLocalStorage(userResponse?: UserResponse) {
    try {
      debugger;
      if (userResponse == null || !userResponse) {
        return;
      }
      // Convert userResponse object to a JSON string
      const userResponseJSON = JSON.stringify(userResponse);
      localStorage.setItem('user', userResponseJSON);
      console.log('User response saved to local storage!');
    } catch (error) {
      console.log('Error saving user response to local storage: ', error);
    }
  }

  getUserFromLocalStorage(): UserResponse | null {
    try {
      const userResponseJSON = localStorage.getItem('user');
      if (userResponseJSON == null || userResponseJSON == undefined) {
        return null;
      }
      // Parse JSON back to an object
      const userResponse = JSON.parse(userResponseJSON!);

      console.log('User response retrieved from local storage!');
      return userResponse;
    } catch (error) {
      console.log('Error retrieving user response to local storage: ', error);
      return null;
    }
  }

  removeUserFromLocalStorage(): void {
    try {
      localStorage.removeItem('user');
      console.log('User data removed from local storage!');
    } catch (error) {
      console.error('Error removing user data from local storage: ', error);
    }
  }
}
