import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar'; 
import {MatButtonModule} from '@angular/material/button';
import { HomepageComponent } from './pages/homepage/homepage.component';
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';
import { LoginDialogComponent } from './components/login-dialog/login-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserService } from './services/user.service';
import { HttpClientModule } from '@angular/common/http';
import { RegisterDialogComponent } from './components/register-dialog/register-dialog.component';
import { PostSorterComponent } from './components/post-sorter/post-sorter.component';
import { PostComponent } from './components/post/post.component';
import {MatCardModule} from '@angular/material/card'; 
import {MatIconModule} from '@angular/material/icon'; 
import { PostService } from './services/post.service';
import { AppRoutingModule } from './app.routing';
import { CommunityPageComponent } from './pages/community-page/community-page.component';
import { CommunityService } from './services/community.service';
import {MatExpansionModule} from '@angular/material/expansion';
import { PostCreatorComponent } from './components/post-creator/post-creator.component';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatMenuModule} from '@angular/material/menu';
import { EditPostDialogComponent } from './components/edit-post-dialog/edit-post-dialog.component';
import { CommunityDialogComponent } from './components/community-dialog/community-dialog.component'; 

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    NavBarComponent,
    LoginDialogComponent,
    RegisterDialogComponent,
    PostSorterComponent,
    PostComponent,
    CommunityPageComponent,
    PostCreatorComponent,
    EditPostDialogComponent,
    CommunityDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatCardModule,
    MatIconModule,
    AppRoutingModule,
    MatExpansionModule,
    MatSnackBarModule,
    MatMenuModule
  ],
  providers: [UserService, PostService, CommunityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
